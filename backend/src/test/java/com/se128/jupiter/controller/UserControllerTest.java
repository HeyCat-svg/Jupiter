package com.se128.jupiter.controller;

import com.alibaba.fastjson.JSON;
import com.se128.jupiter.entity.Order;
import com.se128.jupiter.entity.User;
import com.se128.jupiter.util.constant.Constant;
import com.se128.jupiter.util.logutils.LogUtil;
import com.se128.jupiter.util.msgutils.Msg;
import com.alibaba.fastjson.JSONObject;
import com.se128.jupiter.util.msgutils.MsgCode;
import com.se128.jupiter.util.msgutils.MsgUtil;
import com.se128.jupiter.util.sessionutils.SessionUtil;
import net.sf.json.JSONArray;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.JsonPathResultMatchers;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@WebAppConfiguration
public class UserControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;
    private MockHttpSession session;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        session = new MockHttpSession();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void getUserById() {
        try{
            // login
            JSONObject userInfo = new JSONObject();
            userInfo.put("username", "root");
            userInfo.put("password", "root");
            MvcResult loginResult = mockMvc.perform(MockMvcRequestBuilders
                    .post("/login")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(JSON.toJSONString(userInfo))
                    .accept(MediaType.APPLICATION_JSON_UTF8)
            ).andReturn();
            System.out.println(loginResult.getResponse().getContentAsString());

            // get user by userId
            JSONObject param = new JSONObject();
            param.put("userId", 1);
            String responseString = mockMvc.perform(MockMvcRequestBuilders
                    .post("/getUserById")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(JSON.toJSONString(param))
                    .accept(MediaType.APPLICATION_JSON_UTF8)
            ).andExpect(MockMvcResultMatchers.status().isOk())
                    .andDo(MockMvcResultHandlers.print())
                    .andReturn().getResponse().getContentAsString();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void login() {
        try{
            JSONObject param = new JSONObject();
            param.put("username", "root");
            param.put("password", "root");
            String responseString = mockMvc.perform(MockMvcRequestBuilders
                    .post("/login")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(JSON.toJSONString(param))
                    .accept(MediaType.APPLICATION_JSON_UTF8)
                ).andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse().getContentAsString();
            JSONObject respond = (JSONObject) JSON.parseObject(responseString);
            assertEquals("登陆测试失败",0, respond.get("status"));
            System.out.println(responseString);
        } catch (Exception e){
            e.printStackTrace();
        }
        try{
            JSONObject param = new JSONObject();
            param.put("username", "root");
            param.put("password", "root1");
            String responseString = mockMvc.perform(MockMvcRequestBuilders
                    .post("/login")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(JSON.toJSONString(param))
                    .accept(MediaType.APPLICATION_JSON_UTF8)
            ).andExpect(MockMvcResultMatchers.status().isOk())
                    .andDo(MockMvcResultHandlers.print())
                    .andReturn().getResponse().getContentAsString();
            JSONObject respond = (JSONObject) JSON.parseObject(responseString);
            assertEquals("错误密码",-1, respond.get("status"));
            System.out.println(responseString);
        } catch (Exception e){
            e.printStackTrace();
        }
        try{
            JSONObject param = new JSONObject();
            param.put("username", "ban");
            param.put("password", "ban");
            String responseString = mockMvc.perform(MockMvcRequestBuilders
                    .post("/login")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(JSON.toJSONString(param))
                    .accept(MediaType.APPLICATION_JSON_UTF8)
            ).andExpect(MockMvcResultMatchers.status().isOk())
                    .andDo(MockMvcResultHandlers.print())
                    .andReturn().getResponse().getContentAsString();
            JSONObject respond = (JSONObject) JSON.parseObject(responseString);
            assertEquals("禁用用户",-1, respond.get("status"));
            System.out.println(responseString);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    @Transactional
    @Rollback(value = true)
    public void register() {
        try{
            // 测试重复用户名测试
            String username = "root";
            String password = "root";
            JSONObject param = new JSONObject();
            param.put("username", username);
            param.put("password", password);
            param.put("phone", 1);
            String responseString = mockMvc.perform(MockMvcRequestBuilders
                    .post("/register")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(JSON.toJSONString(param))
                    .accept(MediaType.APPLICATION_JSON_UTF8)
            ).andExpect(MockMvcResultMatchers.status().isOk())
                    .andDo(MockMvcResultHandlers.print())
                    .andReturn().getResponse().getContentAsString();
            JSONObject respond = (JSONObject) JSON.parseObject(responseString);
            assertEquals("重复用户名测试失败",-102, respond.get("status"));
            System.out.println(responseString);

            // 测试注册
            username = "user2";
            password = "user2";
            param.put("username", username);
            param.put("password", password);
            responseString = mockMvc.perform(MockMvcRequestBuilders
                    .post("/register")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(JSON.toJSONString(param))
                    .accept(MediaType.APPLICATION_JSON_UTF8)
            ).andExpect(MockMvcResultMatchers.status().isOk())
                    .andDo(MockMvcResultHandlers.print())
                    .andReturn().getResponse().getContentAsString();
            respond = (JSONObject) JSON.parseObject(responseString);
            assertEquals("新注册用户失败", -102, respond.get("status"));
            System.out.println(responseString);

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void logout() {
        try{
            JSONObject param = new JSONObject();
            String responseString = mockMvc.perform(MockMvcRequestBuilders
                    .post("/logout")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(JSON.toJSONString(param))
                    .accept(MediaType.APPLICATION_JSON_UTF8)
            ).andExpect(MockMvcResultMatchers.status().isOk())
                    .andDo(MockMvcResultHandlers.print())
                    .andReturn().getResponse().getContentAsString();
            JSONObject respond = (JSONObject) JSON.parseObject(responseString);
            assertEquals("登出失败", -1, respond.get("status"));
            System.out.println(responseString);
        } catch (Exception e){
            e.printStackTrace();
        }

        try{
            // 先登录才能登出
            JSONObject userInfo = new JSONObject();
            userInfo.put("username", "root");
            userInfo.put("password", "root");
            MvcResult loginResult = mockMvc.perform(MockMvcRequestBuilders
                    .post("/login")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(JSON.toJSONString(userInfo))
                    .accept(MediaType.APPLICATION_JSON_UTF8)
                    .session(session)
            ).andReturn();
            System.out.println(loginResult.getResponse().getContentAsString());

            JSONObject param = new JSONObject();
            String responseString = mockMvc.perform(MockMvcRequestBuilders
                    .post("/logout")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(JSON.toJSONString(param))
                    .accept(MediaType.APPLICATION_JSON_UTF8)
                    .session(session)
            ).andExpect(MockMvcResultMatchers.status().isOk())
                    .andDo(MockMvcResultHandlers.print())
                    .andReturn().getResponse().getContentAsString();
            JSONObject respond = (JSONObject) JSON.parseObject(responseString);
            assertEquals("登出失败", 0, respond.get("status"));
            System.out.println(responseString);
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    @Test
    public void checkSession() {

        try{
            String responseString = mockMvc.perform(MockMvcRequestBuilders
                    .post("/checkSession")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content("")
                    .accept(MediaType.APPLICATION_JSON_UTF8)
            ).andExpect(MockMvcResultMatchers.status().isOk())
                    .andDo(MockMvcResultHandlers.print())
                    .andReturn().getResponse().getContentAsString();
        } catch (Exception e){
            e.printStackTrace();
        }

        try{
            JSONObject userInfo = new JSONObject();
            userInfo.put("username", "root");
            userInfo.put("password", "root");
            MvcResult loginResult = mockMvc.perform(MockMvcRequestBuilders
                    .post("/login")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(JSON.toJSONString(userInfo))
                    .accept(MediaType.APPLICATION_JSON_UTF8)
                    .session(session)
            ).andReturn();
            System.out.println(loginResult.getResponse().getContentAsString());

            String responseString = mockMvc.perform(MockMvcRequestBuilders
                    .post("/checkSession")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content("")
                    .accept(MediaType.APPLICATION_JSON_UTF8)
                    .session(session)
            ).andExpect(MockMvcResultMatchers.status().isOk())
                    .andDo(MockMvcResultHandlers.print())
                    .andReturn().getResponse().getContentAsString();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void getOrdersByUserId() {
        try{
            String userId = "1";
            JSONObject param = new JSONObject();
            param.put("userId", userId);
            String responseString = mockMvc.perform(MockMvcRequestBuilders
                    .post("/getOrdersByUserId")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(param.toJSONString())
                    .accept(MediaType.APPLICATION_JSON_UTF8)
            ).andExpect(MockMvcResultMatchers.status().isOk())
                    .andDo(MockMvcResultHandlers.print())
                    .andReturn().getResponse().getContentAsString();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void getAllUsers()
    {
        try{
            String responseString = mockMvc.perform(MockMvcRequestBuilders
                    .post("/getAllUsers")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content("")
                    .accept(MediaType.APPLICATION_JSON_UTF8)
            ).andExpect(MockMvcResultMatchers.status().isOk())
                    .andDo(MockMvcResultHandlers.print())
                    .andReturn().getResponse().getContentAsString();
        } catch (Exception e){
            e.printStackTrace();
        }
    }


    @Test
    public void changeUserStatusByUserId()
    {
        try{
            String userId = "1";
            JSONObject param = new JSONObject();
            param.put("userId", userId);
            String responseString = mockMvc.perform(MockMvcRequestBuilders
                    .post("/changeUserStatusByUserId")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(param.toJSONString())
                    .accept(MediaType.APPLICATION_JSON_UTF8)
            ).andExpect(MockMvcResultMatchers.status().isOk())
                    .andDo(MockMvcResultHandlers.print())
                    .andReturn().getResponse().getContentAsString();
        } catch (Exception e){
            e.printStackTrace();
        }

        try{
            String userId = "100";
            JSONObject param = new JSONObject();
            param.put("userId", userId);
            String responseString = mockMvc.perform(MockMvcRequestBuilders
                    .post("/changeUserStatusByUserId")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(param.toJSONString())
                    .accept(MediaType.APPLICATION_JSON_UTF8)
            ).andExpect(MockMvcResultMatchers.status().isOk())
                    .andDo(MockMvcResultHandlers.print())
                    .andReturn().getResponse().getContentAsString();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void editUser() {
        try{
            User user = new User();
            user.setUserId(2);
            user.setUsername("user2");
            user.setPassword("user2");
            user.setPhone("1111");
            user.setUserType(1);
            user.setBuy0(0);
            user.setBuy1(0);
            user.setBuy2(0);
            user.setBuy3(0);
            net.sf.json.JSONObject data = net.sf.json.JSONObject.fromObject(user);
            String responseString = mockMvc.perform(MockMvcRequestBuilders
                    .post("/editUser")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(data.toString())
                    .accept(MediaType.APPLICATION_JSON_UTF8)
            ).andExpect(MockMvcResultMatchers.status().isOk())
                    .andDo(MockMvcResultHandlers.print())
                    .andReturn().getResponse().getContentAsString();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
