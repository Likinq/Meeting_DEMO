package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.Booking;
import com.example.demo.entity.Chart;
import org.apache.ibatis.annotations.*;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Mapper
public interface BookingMapper extends BaseMapper<Booking> {
    @Select("select * from `booking` where userid=#{userid} " +
            "ORDER BY date DESC, begintime DESC")
    @Results({
            @Result(column = "id",property = "id"),
            @Result(column = "roomid",property = "roomid"),
            @Result(column = "date",property = "date"),
            @Result(column = "begintime",property = "begintime"),
            @Result(column = "finishtime",property = "finishtime"),
            @Result(column = "topic",property = "topic"),
            @Result(column = "person",property = "person"),
            @Result(column = "equipment",property = "equipment"),
            @Result(column = "department",property = "department"),
            @Result(column = "state",property = "state"),
            @Result(column = "userid",property = "userid"),
            @Result(column = "reviewerid",property = "reviewerid"),
            @Result(column = "roomid",property = "roomname",javaType = String.class,
                    one=@One(select = "com.example.demo.mapper.RoomMapper.selectRoomNameById")),
            @Result(column = "userid",property = "username",javaType = String.class,
                    one = @One(select = "com.example.demo.mapper.RoleMapper.findById")),
            @Result(column = "reviewerid",property = "reviewername",javaType = String.class,
                    one = @One(select = "com.example.demo.mapper.RoleMapper.findById"))
    })
    List<Booking> selectByUserid(int userid);

    @Select("select * from `booking` where department=#{department} " +
            " ORDER BY date DESC, begintime DESC")
    @Results({
            @Result(column = "id",property = "id"),
            @Result(column = "roomid",property = "roomid"),
            @Result(column = "date",property = "date"),
            @Result(column = "begintime",property = "begintime"),
            @Result(column = "finishtime",property = "finishtime"),
            @Result(column = "topic",property = "topic"),
            @Result(column = "person",property = "person"),
            @Result(column = "equipment",property = "equipment"),
            @Result(column = "department",property = "department"),
            @Result(column = "state",property = "state"),
            @Result(column = "userid",property = "userid"),
            @Result(column = "reviewerid",property = "reviewerid"),
            @Result(column = "roomid",property = "roomname",javaType = String.class,
                    one = @One(select = "com.example.demo.mapper.RoomMapper.selectRoomNameById")),
            @Result(column = "userid",property = "username",javaType = String.class,
                    one = @One(select = "com.example.demo.mapper.RoleMapper.findById")),
            @Result(column = "reviewerid",property = "reviewername",javaType = String.class,
                    one = @One(select = "com.example.demo.mapper.RoleMapper.findById"))
    })
    List<Booking> selectByDepartment(String department);

    @Select("select * from `booking` where date=#{date} " +
            " ORDER BY date DESC, begintime DESC")
    @Results({
            @Result(column = "id",property = "id"),
            @Result(column = "roomid",property = "roomid"),
            @Result(column = "date",property = "date"),
            @Result(column = "begintime",property = "begintime"),
            @Result(column = "finishtime",property = "finishtime"),
            @Result(column = "topic",property = "topic"),
            @Result(column = "person",property = "person"),
            @Result(column = "equipment",property = "equipment"),
            @Result(column = "department",property = "department"),
            @Result(column = "state",property = "state"),
            @Result(column = "userid",property = "userid"),
            @Result(column = "reviewerid",property = "reviewerid"),
            @Result(column = "roomid",property = "roomname",javaType = String.class,
                    one = @One(select = "com.example.demo.mapper.RoomMapper.selectRoomNameById")),
            @Result(column = "userid",property = "username",javaType = String.class,
                    one = @One(select = "com.example.demo.mapper.RoleMapper.findById")),
            @Result(column = "reviewerid",property = "reviewername",javaType = String.class,
                    one = @One(select = "com.example.demo.mapper.RoleMapper.findById"))
    })
    List<Booking> selectByDate(Date date);

    @Select("select * from `booking` where department=#{department} and state=#{state} " +
            "ORDER BY date ASC, begintime ASC")
    @Results({
            @Result(column = "id",property = "id"),
            @Result(column = "roomid",property = "roomid"),
            @Result(column = "date",property = "date"),
            @Result(column = "begintime",property = "begintime"),
            @Result(column = "finishtime",property = "finishtime"),
            @Result(column = "topic",property = "topic"),
            @Result(column = "person",property = "person"),
            @Result(column = "equipment",property = "equipment"),
            @Result(column = "department",property = "department"),
            @Result(column = "state",property = "state"),
            @Result(column = "userid",property = "userid"),
            @Result(column = "reviewerid",property = "reviewerid"),
            @Result(column = "roomid",property = "roomname",javaType = String.class,
                    one = @One(select = "com.example.demo.mapper.RoomMapper.selectRoomNameById")),
            @Result(column = "userid",property = "username",javaType = String.class,
                    one = @One(select = "com.example.demo.mapper.RoleMapper.findById")),
            @Result(column = "reviewerid",property = "reviewername",javaType = String.class,
                    one = @One(select = "com.example.demo.mapper.RoleMapper.findById"))
    })
    List<Booking> selectByDepartmentAndState(String department, String state);

    @Select("select * from `booking` where department=#{department} and date=#{date} " +
            "ORDER BY date DESC, begintime DESC")
    @Results({
            @Result(column = "id",property = "id"),
            @Result(column = "roomid",property = "roomid"),
            @Result(column = "date",property = "date"),
            @Result(column = "begintime",property = "begintime"),
            @Result(column = "finishtime",property = "finishtime"),
            @Result(column = "topic",property = "topic"),
            @Result(column = "person",property = "person"),
            @Result(column = "equipment",property = "equipment"),
            @Result(column = "department",property = "department"),
            @Result(column = "state",property = "state"),
            @Result(column = "userid",property = "userid"),
            @Result(column = "reviewerid",property = "reviewerid"),
            @Result(column = "roomid",property = "roomname",javaType = String.class,
                    one = @One(select = "com.example.demo.mapper.RoomMapper.selectRoomNameById")),
            @Result(column = "userid",property = "username",javaType = String.class,
                    one = @One(select = "com.example.demo.mapper.RoleMapper.findById")),
            @Result(column = "reviewerid",property = "reviewername",javaType = String.class,
                    one = @One(select = "com.example.demo.mapper.RoleMapper.findById"))
    })
    List<Booking> selectByDepartmentAndDate(String department, Date date);

//    @Select("select * from `booking` where begintime<=#{begintime} and finishtime>=#{finishtime}")
//    List<Booking> selectBusyBookingBF(Time begintime, Time finishtime);
//
//    @Select("select * from `booking` where date=#{date} and begintime<=#{begintime} and finishtime>=#{finishtime}")
//    List<Booking> selectBusyBookingDBF(Date date, Time begintime, Time finishtime);

    @Select("select * from `booking` where roomid=#{roomid} ORDER BY date ASC, begintime ASC")
    @Results({
            @Result(column = "id",property = "id"),
            @Result(column = "roomid",property = "roomid"),
            @Result(column = "date",property = "date"),
            @Result(column = "begintime",property = "begintime"),
            @Result(column = "finishtime",property = "finishtime"),
            @Result(column = "topic",property = "topic"),
            @Result(column = "person",property = "person"),
            @Result(column = "equipment",property = "equipment"),
            @Result(column = "department",property = "department"),
            @Result(column = "state",property = "state"),
            @Result(column = "userid",property = "userid"),
            @Result(column = "reviewerid",property = "reviewerid"),
            @Result(column = "roomid",property = "roomname",javaType = String.class,
                    one = @One(select = "com.example.demo.mapper.RoomMapper.selectRoomNameById")),
            @Result(column = "userid",property = "username",javaType = String.class,
                    one = @One(select = "com.example.demo.mapper.RoleMapper.findById")),
            @Result(column = "reviewerid",property = "reviewername",javaType = String.class,
                    one = @One(select = "com.example.demo.mapper.RoleMapper.findById"))
    })
    List<Booking> selectByRoomid(Integer roomid);

    @Select("select * from `booking` where roomid=#{roomid} and date=#{date} ORDER BY date ASC, begintime ASC")
    @Results({
            @Result(column = "id",property = "id"),
            @Result(column = "roomid",property = "roomid"),
            @Result(column = "date",property = "date"),
            @Result(column = "begintime",property = "begintime"),
            @Result(column = "finishtime",property = "finishtime"),
            @Result(column = "topic",property = "topic"),
            @Result(column = "person",property = "person"),
            @Result(column = "equipment",property = "equipment"),
            @Result(column = "department",property = "department"),
            @Result(column = "state",property = "state"),
            @Result(column = "userid",property = "userid"),
            @Result(column = "reviewerid",property = "reviewerid"),
            @Result(column = "roomid",property = "roomname",javaType = String.class,
                    one = @One(select = "com.example.demo.mapper.RoomMapper.selectRoomNameById")),
            @Result(column = "userid",property = "username",javaType = String.class,
                    one = @One(select = "com.example.demo.mapper.RoleMapper.findById")),
            @Result(column = "reviewerid",property = "reviewername",javaType = String.class,
                    one = @One(select = "com.example.demo.mapper.RoleMapper.findById"))
    })
    List<Booking> selectAllByRoomidAndDate(Integer roomid,Date date);

    @Select("select count(*) from `booking`")
    int count();

    @Select("select date,count(*) AS count from `booking` where date BETWEEN #{startDate} AND #{endDate} " +
            "GROUP BY date ORDER BY date ASC")
    List<Chart> selectChartByDate(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    @Select("select * from `booking` ORDER BY date DESC, begintime DESC")
    @Results({
            @Result(column = "id",property = "id"),
            @Result(column = "roomid",property = "roomid"),
            @Result(column = "date",property = "date"),
            @Result(column = "begintime",property = "begintime"),
            @Result(column = "finishtime",property = "finishtime"),
            @Result(column = "topic",property = "topic"),
            @Result(column = "person",property = "person"),
            @Result(column = "equipment",property = "equipment"),
            @Result(column = "department",property = "department"),
            @Result(column = "state",property = "state"),
            @Result(column = "userid",property = "userid"),
            @Result(column = "reviewerid",property = "reviewerid"),
            @Result(column = "roomid",property = "roomname",javaType = String.class,
                    one = @One(select = "com.example.demo.mapper.RoomMapper.selectRoomNameById")),
            @Result(column = "userid",property = "username",javaType = String.class,
                    one = @One(select = "com.example.demo.mapper.RoleMapper.findById")),
            @Result(column = "reviewerid",property = "reviewername",javaType = String.class,
                    one = @One(select = "com.example.demo.mapper.RoleMapper.findById"))
    })
    List<Booking> selectAll();

    @Select("select begintime,finishtime from booking where roomid=#{roomid} and date=#{date}")
    List<Booking> selectByRoomidAndDate(int roomid, Date date);


}
