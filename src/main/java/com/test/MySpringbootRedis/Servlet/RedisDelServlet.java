package com.test.MySpringbootRedis.Servlet;

import com.test.MySpringbootRedis.entity.Student;
import lombok.SneakyThrows;
import org.springframework.data.redis.connection.RedisZSetCommands;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

@WebServlet("/redisDelServlet")
public class RedisDelServlet extends HttpServlet {
    @Resource
    private RedisTemplate<String,Object> redisTemplate=null;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       doPost(req,resp);
    }

    @SneakyThrows
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //获取从jsp页面form表单传过来的值
        String field=req.getParameter("field");



        //从hash中删除指定field
        redisTemplate.opsForHash().delete("stuHashMap",field);
       //从sortedSet中删除指定field
        redisTemplate.opsForZSet().remove("avgScoreZSet",field);


        ZSetOperations<String, Object> zSetOperations = redisTemplate.opsForZSet();
        //声明一个排序对象rang
        RedisZSetCommands.Range range= RedisZSetCommands.Range.range();
        //声明分页对象limit
        RedisZSetCommands.Limit limit= RedisZSetCommands.Limit.limit().offset((MyRedisServlet.nowPage-1)*10).count(MyRedisServlet.pageNum);
        //获取分页和排序后的field属性集合
        Set<Object> set = zSetOperations.rangeByLex("avgScoreZSet", range, limit);

        //根据取出的学号集合从hash中获取数据
        List<Object> studentList = redisTemplate.opsForHash().multiGet("stuHashMap", set);


        //将从sortedSet中取出的属性名以及根据属性名取出的student的集合一起传到jsp页面
        List<Student> newStuList=new ArrayList<>();
        int count=0;
        for (Object o : set) {
            if (count<studentList.size()) {
                Student student= (Student) studentList.get(count++);
                student.setField((String) o);//将sortedSet中的值存到student对象中
                newStuList.add(student); //将setudent对象加入新的集合中，传到jsp页面
            }
        }

        //req中设置参数
        req.setAttribute("List",newStuList);
        //当前页数
        req.setAttribute("nowPage",MyRedisServlet.nowPage);
        //转发到jsp页面
        req.getRequestDispatcher("/test.jsp").forward(req,resp);

    }
}
