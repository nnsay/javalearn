package com.neuralgalaxy.apps;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.neuralgalaxy.apps.dao.mapper.UserMapper;
import com.neuralgalaxy.apps.dao.entity.User;
import com.neuralgalaxy.apps.dao.service.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DBTest {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserService userService;

    @Test
    public void TestGetListUser() {
        List<User> list = userMapper.selectList(null);
        Assert.assertTrue(">0", list.size() > 0);
    }

    @Test
    public void TestGetSelectColumn() {
        QueryWrapper<User> query = Wrappers.query();
        // query.select("id", "createdAt");
        query.select(User.class, u -> !u.getColumn().equals("createdAt"));
        List<User> list = userMapper.selectList(query);
        Assert.assertTrue(">0", list.size() > 0);
    }

    @Test
    public void TestBuildQuery() {
        QueryWrapper<User> query = Wrappers.query();
        query.like("firstName", "admin")
                .eq("isActive", true)
                .or()
                .likeLeft("email", "neuralgalaxy.com")
                .orderByDesc("id");
        List<User> userList = userMapper.selectList(query);
        userList.forEach(System.out::println);
    }

    @Test
    public void TestBuildQueryBySql() {
        QueryWrapper<User> query = Wrappers.query();
        query.apply("date_format(createdAt, '%Y-%m-%d')={0}", "2022-03-11")
                .inSql("id", "select id from Users where firstName like '%admin%'");
        List<User> userList = userMapper.selectList(query);
        userList.forEach(System.out::println);
    }

    @Test
    public void TestBuildQueryByLambda() {
        QueryWrapper<User> query = new QueryWrapper<>();
        query.nested(wq -> wq.like("firstName", "admin"))
                .and(qw -> qw.between("id", 0, 5).or().eq("isActive", true))
                .last("limit 1");
        List<User> userList = userMapper.selectList(query);
        userList.forEach(System.out::println);
    }

    @Test
    public void TestBuildQueryByLambda2() {
        List<User> userList = new LambdaQueryChainWrapper<>(userMapper)
                .select(User::getId, User::getEmail)
                .like(User::getAccountName, "admin")
                .list();
        userList.forEach(System.out::println);
    }

    @Test
    public void TestByPage() {
        Page<User> page = new Page<>(1, 2);

        // way1: get ipage from mapper
        // QueryWrapper<User> query = Wrappers.query();
        // query.gt("id", 0);
        // IPage<User> iPage = userMapper.selectPage(page, query);

        // way2: get ipage from
        IPage<User> iPage = new LambdaQueryChainWrapper<>(userMapper)
                .select(User::getId, User::getEmail)
                .like(User::getEmail, "neuralgalaxy")
                .page(page);

        System.out.printf("总页数:%d\n", iPage.getPages());
        System.out.printf("总记录数:%d\n", iPage.getTotal());
        List<User> userList = iPage.getRecords();
        userList.forEach(System.out::println);
    }

    @Test
    public void serviceTest() {
        User user = userService.getById(1);
        System.out.println(user);
    }

    @Test
    public void servicePage() {
        Page<User> page = new Page<>(1, 2);
        QueryWrapper<User> query = Wrappers.query();
        query.orderByDesc("id");

        Page<User> userPage = userService.page(page, query);
        System.out.printf("总页数:%d\n", userPage.getPages());
        System.out.printf("总记录数:%d\n", userPage.getTotal());
    }

    @Test
    public void codeGenerate() {
        FastAutoGenerator.create(
                        "jdbc:mysql://mysql:3306/ngiqdev?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC",
                        "root",
                        "1qaz"
                )
                .globalConfig(builder -> {
                    builder
                            .author("jimmy")
                            //.enableSwagger() // 开启 swagger 模式
                            .outputDir("/tmp/sqlcodegen"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.neuralgalaxy.apps") // 设置父包名
                            .moduleName("dao"); // 设置父包模块名
                })
                .strategyConfig(builder -> {
                    builder.addInclude("Users"); // 设置需要生成的表名
                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }
}
