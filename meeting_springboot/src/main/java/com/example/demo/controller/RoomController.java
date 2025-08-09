package com.example.demo.controller;

import com.example.demo.entity.Booking;
import com.example.demo.entity.Room;
import com.example.demo.mapper.BookingMapper;
import com.example.demo.mapper.RoomMapper;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;


@RestController
@RequestMapping("/room")
@CrossOrigin(origins = "*")
public class RoomController {
    @Resource
    RoomMapper roomMapper;

    @Resource
    BookingMapper bookingMapper;

    @GetMapping("/list")
    public List<Room> selectAll() {return roomMapper.selectList(null);}

    @GetMapping("/nlist")
    public List<Booking> selectByRoomid(@RequestParam Integer roomid) {return bookingMapper.selectByRoomid(roomid);}

    @GetMapping("/ndlist")
    public List<Booking> selectByRoomidAndDate(@RequestParam Integer roomid,@RequestParam Date date) {return bookingMapper.selectAllByRoomidAndDate(roomid,date);}

    @PostMapping("/add")
    public void insert(@RequestBody Room room){roomMapper.insert(room);}

    @PostMapping("/update")
    public void update(@RequestBody Room room) {roomMapper.updateById(room);}

    @GetMapping("/delete")
    public void delete(@RequestParam Integer id) {roomMapper.deleteById(id);}

//    @GetMapping("/bflist")
//    public List<Room> getAvailableRooms(@RequestParam Time begintime,
//                                        @RequestParam Time finishtime) {
//        List<Booking> busyBookings = bookingMapper.selectBusyBookingBF(begintime, finishtime);
//
//        List<String> busyRoomNames = busyBookings.stream()
//                .map(Booking::getRoomname)
//                .toList();
//        String formattedRoomNames = busyRoomNames.stream()
//                .map(name -> "\"" + name + "\"")
//                .collect(Collectors.joining(","));
//        return roomMapper.selectByRoomname(formattedRoomNames);
//    }

//    @GetMapping("/nbflist")
//    public List<Room> getAvailableRooms1(@RequestParam Date date,
//                                        @RequestParam Time begintime,
//                                        @RequestParam Time finishtime) {
//        List<Booking> busyBookings = bookingMapper.selectBusyBookingDBF(date, begintime, finishtime);
//
//        List<String> busyRoomNames = busyBookings.stream()
//                .map(Booking::getRoomname)
//                .toList();
//        String formattedRoomNames = busyRoomNames.stream()
//                .map(name -> "\"" + name + "\"")
//                .collect(Collectors.joining(","));
//        return roomMapper.selectByRoomname(formattedRoomNames);
//    }

    @GetMapping("/count")
    public int count() {return roomMapper.count();}
}
