package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.Room;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.sql.Time;
import java.util.List;


@Mapper
public interface RoomMapper extends BaseMapper<Room> {
    //@Select("select * from `room` where roomname not in (#{roomname})")
    @Select("select * from `room` where roomname not in (${roomname})")
    List<Room> selectByRoomname(String roomname);

    @Select("select count(*) from `room`")
    int count();

    @Select("select roomname from room where id=#{id}")
    String selectRoomNameById(Integer id);

    @Select("select id from room where roomname=#{roomname}")
    Integer selectIdByRoomname(String roomname);

    @Select("select open,close from room where id=#{roomid}")
    Room selectTimeById(Integer roomid);


}
