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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@WebServlet("/myRedisServlet")
public class MyRedisServlet extends HttpServlet {



    public static int pageNum=10;//每页查询10条数据
    public static int totalPage=0;//一共有多少页，默认为0
    public static int nowPage=1;//当前页数


    @Resource
    private RedisTemplate<String,Object> redisTemplate=null;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

//        //初始化数据，利用循环往redis中存入20条数据
//        HashMap<String,Object> map=new HashMap<>();//定义一个map，用来存放student对象
//        for (int i = 1; i <= 20; i++) {
//            Student student = new Student();//实例化student对象
//           student.setStuId(1611030001+i+"");//学生学号
//            student.setStuName("student" + i);//学生姓名
//            //当前时间作为学生日期，获取当前时间，在格式化成"yyyy-MM-dd"的时间字符串，在将时间字符串转换位timstamp格式
//            try {
//                student.setBirthday( new Timestamp(new SimpleDateFormat("yyyy-MM-dd").parse(new SimpleDateFormat("yyyy-MM-dd").format(new Timestamp(System.currentTimeMillis()))).getTime()));
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//            student.setDescription("三好学生");//描述
//            student.setAvgscore(100 - i);//平均分
//
//            //将随机产生的uuid作为属性filed，student对象作为值
//            UUID uuid = UUID.randomUUID();//随机产生学号
//            map.put(uuid+"",student);
//
//            //将学号(将学号当作hash里面的属性)和学生平均分存到sortedSet
//            redisTemplate.opsForZSet().add("avgScoreZSet",uuid+"",student.getAvgscore());
//        }
//
//        //将student对象以hash类型存到redis中，stuMap是key
//        redisTemplate.opsForHash().putAll("stuHashMap",map);

        //获取总页数
        MyRedisServlet.totalPage = (int)Math.ceil(redisTemplate.opsForZSet().size("avgScoreZSet").doubleValue() / MyRedisServlet.pageNum);
        //上一页，下一页
        String page = req.getParameter("nowPage");
        if (page!=null) {
            nowPage = Integer.valueOf(page);
        }


        ZSetOperations<String, Object> zSetOperations = redisTemplate.opsForZSet();
        //声明一个排序对象rang
        RedisZSetCommands.Range range= RedisZSetCommands.Range.range();
        //声明分页对象limit
        RedisZSetCommands.Limit limit= RedisZSetCommands.Limit.limit().offset((MyRedisServlet.nowPage-1)*10).count(MyRedisServlet.pageNum);
        //获取分页和排序后的属性field集合
        Set<Object> set = zSetOperations.rangeByLex("avgScoreZSet", range, limit);

        //根据取出的属性field集合从hash中获取数据
        List<Object> studentList = redisTemplate.opsForHash().multiGet("stuHashMap", set);



        //将从sortedSet中取出的属性名以及根据属性名取出的student的集合一起传到jsp页面
        List<Student> newStuList=new ArrayList<>();
        int count=0;
        for (Object o : set) {
            if (count<studentList.size()) {
                Student student= (Student) studentList.get(count++);
                student.setField((String) o);//将sortedSet中的值存到student对象中
                newStuList.add(student); //setudent对象加入新的集合中，传到jsp页面
            }
        }

        //所有页数，传给jsp页面
        List<Integer> pageList=new ArrayList<>();
        for (int i=1;i<=totalPage;i++){
            pageList.add(i);
        }

        //req中设置参数
        req.setAttribute("List",newStuList);
        //当前页数
        req.setAttribute("nowPage",MyRedisServlet.nowPage);
        //所有的页数
        req.setAttribute("pageList",pageList);
        //总页数
        req.setAttribute("totalPage",totalPage);
        //转发到jsp页面
        req.getRequestDispatcher("/test.jsp").forward(req,resp);

    }
}
