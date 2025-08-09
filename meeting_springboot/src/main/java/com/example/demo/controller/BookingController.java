package com.example.demo.controller;

import com.example.demo.entity.Booking;
import com.example.demo.entity.Chart;
import com.example.demo.entity.Room;
import com.example.demo.entity.TimeSlot;
import com.example.demo.mapper.BookingMapper;
import com.example.demo.mapper.RoomMapper;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/book")
@CrossOrigin(origins = "*")
public class BookingController {
    @Resource
    private BookingMapper bookingMapper;

    @Resource
    private RoomMapper roomMapper;

    @GetMapping("/list")
    public List<Booking> listall() {return bookingMapper.selectAll();}

    @GetMapping("/mylist")
    public List<Booking> list(@RequestParam Integer userid) {return bookingMapper.selectByUserid(userid);}

    @GetMapping("/departmentlist")
    public List<Booking> listDepartment(@RequestParam String department) {return bookingMapper.selectByDepartment(department);}

    @GetMapping("/datelist")
    public List<Booking> listDate(@RequestParam Date date) {return bookingMapper.selectByDate(date);}

    @GetMapping("/ddlist")
    public List<Booking> listDateAndDep(@RequestParam Date date,@RequestParam String department) {return bookingMapper.selectByDepartmentAndDate(department, date);}

    @GetMapping("/reviewlist")
    public List<Booking> listReview(@RequestParam String department) {return bookingMapper.selectByDepartmentAndState(department,"待审核");}

    @PostMapping("/add")
    public void add(@RequestBody Booking booking) {
        LocalTime originalTime = booking.getFinishtime().toLocalTime();
        LocalTime newTime = originalTime.plusMinutes(30);
        booking.setFinishtime(Time.valueOf(newTime));
        bookingMapper.insert(booking);
    }

    @PostMapping("/update")
    public void update(@RequestBody Booking booking) {bookingMapper.updateById(booking);}

    @GetMapping("/delete")
    public void delete(@RequestParam Integer id) {bookingMapper.deleteById(id);}

    @GetMapping("/count")
    public int count() {return bookingMapper.count();}

    @GetMapping("/chart")
    public List<Chart> getchart() {
        LocalDate currentDate = LocalDate.now();
        LocalDate startDate = currentDate.minusDays(7);  // 前7天
        LocalDate endDate = currentDate.plusDays(7);     // 后7天

        // 生成完整的日期范围列表（转为 java.sql.Date）
        List<java.sql.Date> allDates = startDate.datesUntil(endDate.plusDays(1))
                .map(Date::valueOf)  // 直接转换为 java.sql.Date
                .toList();

        // 查询数据库中有会议的日期（参数类型改为 java.sql.Date）
        List<Chart> dbResults = bookingMapper.selectChartByDate(
                java.sql.Date.valueOf(startDate),
                java.sql.Date.valueOf(endDate)
        );

        // 合并结果，填充缺失日期为 count=0
        return allDates.stream()
                .map(sqlDate -> {
                    // 将 java.sql.Date 转为 yyyy-MM-dd 格式字符串再比较
                    String dateStr = sqlDate.toString();
                    Optional<Chart> match = dbResults.stream()
                            .filter(dto -> dateStr.equals(dto.getDate().toString()))
                            .findFirst();
                    return match.orElse(new Chart(sqlDate, 0)); // 确保填充默认值
                })
                .collect(Collectors.toList());
    }

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
    @GetMapping("/timeslots")
    public List<TimeSlot> getTimeSlots(@RequestParam Integer roomid,@RequestParam Date date) {
        List<TimeSlot> result = new ArrayList<>();
        Room room = roomMapper.selectTimeById(roomid);
        List<Booking> bookings = bookingMapper.selectByRoomidAndDate(roomid,date);
        LocalTime openTime = room.getOpen().toLocalTime();
        LocalTime closeTime = room.getClose().toLocalTime();

        // 生成半小时间隔的时间点
        List<LocalTime> timePoints = new ArrayList<>();
        LocalTime current = openTime;
        while (current.isBefore(closeTime)) {
            timePoints.add(current);
            current = current.plusMinutes(30);
        }

        for (int i = 0; i < timePoints.size() - 1; i++) {
            LocalTime slotStart = timePoints.get(i);
            LocalTime slotEnd = timePoints.get(i + 1);

            String timeRange = String.format("%s~%s",
                    slotStart.format(TIME_FORMATTER),
                    slotEnd.format(TIME_FORMATTER));

            boolean isBooked = bookings.stream().anyMatch(booking -> {
                LocalTime bookingStart = booking.getBegintime().toLocalTime();
                LocalTime bookingEnd = booking.getFinishtime().toLocalTime();
                return (slotStart.isBefore(bookingEnd) && slotEnd.isAfter(bookingStart));
            });


            result.add(new TimeSlot(
                    slotStart,
                    timeRange,
                    isBooked
            ));
        }

        return result;
    }

    @GetMapping("/timeslots2")
    public List<TimeSlot> getMultiDayTimeSlots(
            @RequestParam Integer roomid,
            @RequestParam List<Date> dates) {

        List<TimeSlot> result = new ArrayList<>();
        Room room = roomMapper.selectTimeById(roomid);
        LocalTime openTime = room.getOpen().toLocalTime();
        LocalTime closeTime = room.getClose().toLocalTime();

        // 生成半小时间隔的时间点
        List<LocalTime> timePoints = new ArrayList<>();
        LocalTime current = openTime;
        while (current.isBefore(closeTime)) {
            timePoints.add(current);
            current = current.plusMinutes(30);
        }
        System.out.printf(dates.toString());
        // 检查每个时间段在所有日期中的占用情况
        for (int i = 0; i < timePoints.size() - 1; i++) {
            LocalTime slotStart = timePoints.get(i);
            LocalTime slotEnd = timePoints.get(i + 1);
            String timeRange = String.format("%s~%s",
                    slotStart.format(TIME_FORMATTER),
                    slotEnd.format(TIME_FORMATTER));

            // 检查该时间段在所有日期中是否都空闲
            boolean allFree = dates.stream().allMatch(date -> {
                List<Booking> dayBookings = bookingMapper.selectByRoomidAndDate(roomid, date);
                return dayBookings.stream().noneMatch(booking -> {
                    LocalTime bookingStart = booking.getBegintime().toLocalTime();
                    LocalTime bookingEnd = booking.getFinishtime().toLocalTime();
                    return slotStart.isBefore(bookingEnd) && slotEnd.isAfter(bookingStart);
                });
            });

            result.add(new TimeSlot(slotStart, timeRange, !allFree));
        }

        return result;
    }
}
