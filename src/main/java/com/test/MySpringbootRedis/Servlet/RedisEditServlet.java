package com.test.MySpringbootRedis.Servlet;

import com.test.MySpringbootRedis.entity.Student;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

@WebServlet("/redisEditServlet")
public class RedisEditServlet extends HttpServlet {
    @Resource
    private RedisTemplate<String,Object> redisTemplate=null;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //获取从jsp页面form表单传过来的值
        String field=req.getParameter("field");
        String stuId=req.getParameter("stuId");
        String stuName=req.getParameter("stuName");
        String birthday=req.getParameter("birthday");
        String description=req.getParameter("description");
        String avgscore=req.getParameter("avgscore");
        //实例化一个stunent对象，将值存进对象
        Student stu=new Student();
        stu.setStuId(stuId);
        stu.setStuName(stuName);
        stu.setBirthday(new Timestamp(System.currentTimeMillis()).valueOf(birthday));
        stu.setDescription(description);
        stu.setAvgscore(Integer.valueOf(avgscore));
        System.out.println(field);
       redisTemplate.opsForHash().put("stuHashMap",field,stu);
        ZSetOperations<String, Object> zSetOperations = redisTemplate.opsForZSet();
        //声明一个排序对象rang
        RedisZSetCommands.Range range= RedisZSetCommands.Range.range();
        //声明分页对象limit
        RedisZSetCommands.Limit limit= RedisZSetCommands.Limit.limit().offset((MyRedisServlet.nowPage-1)*10).count(MyRedisServlet.pageNum);
        //获取分页和排序后的属性集合
        Set<Object> set = zSetOperations.rangeByLex("avgScoreZSet", range, limit);

        //根据取出的属性集合从hash中获取数据
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
