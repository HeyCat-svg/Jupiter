package com.se128.jupiter.controller;

import com.alibaba.fastjson.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.transaction.Transactional;
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@WebAppConfiguration
class GoodsControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getGoodsByGoodsId() {
        //OK
        try{
            String goodsId = "513";
            JSONObject param = new JSONObject();
            param.put("goodsId", goodsId);
            String responseString = mockMvc.perform(MockMvcRequestBuilders
                    .post("/getGoodsByGoodsId")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(JSON.toJSONString(param))
                    .accept(MediaType.APPLICATION_JSON_UTF8)
            ).andExpect(MockMvcResultMatchers.status().isOk())
                    .andDo(MockMvcResultHandlers.print())
                    .andReturn().getResponse().getContentAsString();
        } catch (Exception e){
            e.printStackTrace();
        }
        //No such goodsId
        try{
            String goodsId = "200";
            JSONObject param = new JSONObject();
            param.put("goodsId", goodsId);
            String responseString = mockMvc.perform(MockMvcRequestBuilders
                    .post("/getGoodsByGoodsId")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(JSON.toJSONString(param))
                    .accept(MediaType.APPLICATION_JSON_UTF8)
            ).andExpect(MockMvcResultMatchers.status().isOk())
                    .andDo(MockMvcResultHandlers.print())
                    .andReturn().getResponse().getContentAsString();
        } catch (Exception e){
            e.printStackTrace();
        }
        //No goodsId
        try{
            JSONObject param = new JSONObject();
            String responseString = mockMvc.perform(MockMvcRequestBuilders
                    .post("/getGoodsByGoodsId")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(param.toString())
                    .accept(MediaType.APPLICATION_JSON_UTF8)
            ).andExpect(MockMvcResultMatchers.status().isOk())
                    .andDo(MockMvcResultHandlers.print())
                    .andReturn().getResponse().getContentAsString();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    void getGoodsByName() {
        //Ok
        try{
            String name = "水";
            JSONObject param = new JSONObject();
            param.put("name", name);
            String responseString = mockMvc.perform(MockMvcRequestBuilders
                    .post("/getGoodsByName")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(JSON.toJSONString(param))
                    .accept(MediaType.APPLICATION_JSON_UTF8)
            ).andExpect(MockMvcResultMatchers.status().isOk())
                    .andDo(MockMvcResultHandlers.print())
                    .andReturn().getResponse().getContentAsString();
        } catch (Exception e){
            e.printStackTrace();
        }
        //Error
        try{
            String responseString = mockMvc.perform(MockMvcRequestBuilders
                    .post("/getGoodsByName")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content("")
                    .accept(MediaType.APPLICATION_JSON_UTF8)
            ).andExpect(MockMvcResultMatchers.status().is4xxClientError())
                    .andDo(MockMvcResultHandlers.print())
                    .andReturn().getResponse().getContentAsString();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    void getAllGoods() {
        //OK
        try{
            String pageId = "0";
            String pageSize = "10";
            String goodsType = "-1";
            JSONObject param = new JSONObject();
            param.put("pageId", pageId);
            param.put("pageSize", pageSize);
            param.put("goodsType", goodsType);
            String responseString = mockMvc.perform(MockMvcRequestBuilders
                    .post("/getAllGoods")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(JSON.toJSONString(param))
                    .accept(MediaType.APPLICATION_JSON_UTF8)
            ).andExpect(MockMvcResultMatchers.status().isOk())
                    .andDo(MockMvcResultHandlers.print())
                    .andReturn().getResponse().getContentAsString();
        } catch (Exception e){
            e.printStackTrace();
        }
        //Error
        try{
            String responseString = mockMvc.perform(MockMvcRequestBuilders
                    .post("/getAllGoods")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content("")
                    .accept(MediaType.APPLICATION_JSON_UTF8)
            ).andExpect(MockMvcResultMatchers.status().is4xxClientError())
                    .andDo(MockMvcResultHandlers.print())
                    .andReturn().getResponse().getContentAsString();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    void editGoods() {
        //OK
        try{
            String goodsId = "2000";
            String name = "SJTU毕业会";
            String start_time = "2020-08-07";
            String end_time = "2020-08-07";
            String address = "菁菁堂";
            String website = "i.sjtu.edu.cn";
            String goodsType = "0";
            String image = "0";
            String viewCounter = "0";
            String buyCounter = "0";
            JSONObject param = new JSONObject();
            param.put("goodsId", goodsId);
            param.put("name", name);
            param.put("startTime", start_time);
            param.put("endTime", end_time);
            param.put("address", address);
            param.put("website", website);
            param.put("goodsType", goodsType);
            param.put("image", image);
            param.put("viewCounter",viewCounter);
            param.put("buyCounter",buyCounter);
            String responseString = mockMvc.perform(MockMvcRequestBuilders
                    .post("/editGoods")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(JSON.toJSONString(param))
                    .accept(MediaType.APPLICATION_JSON_UTF8)
            ).andExpect(MockMvcResultMatchers.status().isOk())
                    .andDo(MockMvcResultHandlers.print())
                    .andReturn().getResponse().getContentAsString();
        } catch (Exception e){
            e.printStackTrace();
        }
        //Error
        try{
            String goodsId = "100";
            String name = "SJTU毕业会";
            String start_time = "2020-08-07";
            String end_time = "2020-08-07";
            String address = "菁菁堂";
            String website = "i.sjtu.edu.cn";
            String goodsType = "0";
            String image = "0";
            String viewCounter = "0";
            String buyCounter = "0";
            JSONObject param = new JSONObject();
            param.put("goodsId", goodsId);
            param.put("name", name);
            param.put("startTime", start_time);
            param.put("endTime", end_time);
            param.put("address", address);
            param.put("website", website);
            param.put("goodsType", goodsType);
            param.put("image", image);
            param.put("viewCounter",viewCounter);
            param.put("buyCounter",buyCounter);
            String responseString = mockMvc.perform(MockMvcRequestBuilders
                    .post("/editGoods")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(JSON.toJSONString(param))
                    .accept(MediaType.APPLICATION_JSON_UTF8)
            ).andExpect(MockMvcResultMatchers.status().isOk())
                    .andDo(MockMvcResultHandlers.print())
                    .andReturn().getResponse().getContentAsString();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    @Transactional
    @Rollback(value = false)
    void addGoods() {
        //OK
        try{
            String price = "188";
            String surplus = "0";
            String time = "2020-08-07 星期五 20:00";
            String ticketType = "预售票（188.00）";
            String name = "SJTU毕业会";
            String startTime = "2020-08-07";
            String endTime = "2020-08-07";
            String address = "菁菁堂";
            String website = "i.sjtu.edu.cn";
            String goodsType = "0";
            String image = "0";
            JSONObject detail = new JSONObject();
            detail.put("price", price);
            detail.put("surplus", surplus);
            detail.put("time", time);
            detail.put("ticketType", ticketType);
            JSONArray goodsDetails = new JSONArray();
            goodsDetails.add(detail);
            JSONObject param = new JSONObject();
            param.put("name", name);
            param.put("startTime", startTime);
            param.put("endTime", endTime);
            param.put("address", address);
            param.put("website", website);
            param.put("goodsType", goodsType);
            param.put("image", image);
            param.put("goodsDetails",goodsDetails.toString());
            String responseString = mockMvc.perform(MockMvcRequestBuilders
                    .post("/addGoods")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(JSON.toJSONString(param))
                    .accept(MediaType.APPLICATION_JSON_UTF8)
            ).andExpect(MockMvcResultMatchers.status().isOk())
                    .andDo(MockMvcResultHandlers.print())
                    .andReturn().getResponse().getContentAsString();
        } catch (Exception e){
            e.printStackTrace();
        }
        //Error
        try{
            String responseString = mockMvc.perform(MockMvcRequestBuilders
                    .post("/addGoods")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content("")
                    .accept(MediaType.APPLICATION_JSON_UTF8)
            ).andExpect(MockMvcResultMatchers.status().is4xxClientError())
                    .andDo(MockMvcResultHandlers.print())
                    .andReturn().getResponse().getContentAsString();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    @Transactional
    @Rollback(value = true)
    void deleteGoodsByGoodsId() {
        //Ok
        try{
            String goodsId = "522";
            JSONObject param = new JSONObject();
            param.put("goodsId", goodsId);
            String responseString = mockMvc.perform(MockMvcRequestBuilders
                    .post("/deleteGoodsByGoodsId")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(JSON.toJSONString(param))
                    .accept(MediaType.APPLICATION_JSON_UTF8)
            ).andExpect(MockMvcResultMatchers.status().isOk())
                    .andDo(MockMvcResultHandlers.print())
                    .andReturn().getResponse().getContentAsString();
        } catch (Exception e){
            e.printStackTrace();
        }
        //Error
        try{
            String responseString = mockMvc.perform(MockMvcRequestBuilders
                    .post("/deleteGoodsByGoodsId")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content("")
                    .accept(MediaType.APPLICATION_JSON_UTF8)
            ).andExpect(MockMvcResultMatchers.status().is4xxClientError())
                    .andDo(MockMvcResultHandlers.print())
                    .andReturn().getResponse().getContentAsString();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    void getPopularGoods() {
        //OK
        try{
            String number = "3";
            JSONObject param = new JSONObject();
            param.put("number", number);
            String responseString = mockMvc.perform(MockMvcRequestBuilders
                    .post("/getPopularGoods")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(JSON.toJSONString(param))
                    .accept(MediaType.APPLICATION_JSON_UTF8)
            ).andExpect(MockMvcResultMatchers.status().isOk())
                    .andDo(MockMvcResultHandlers.print())
                    .andReturn().getResponse().getContentAsString();
        } catch (Exception e){
            e.printStackTrace();
        }
        //Error
        try{
            String responseString = mockMvc.perform(MockMvcRequestBuilders
                    .post("/getPopularGoods")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content("")
                    .accept(MediaType.APPLICATION_JSON_UTF8)
            ).andExpect(MockMvcResultMatchers.status().is4xxClientError())
                    .andDo(MockMvcResultHandlers.print())
                    .andReturn().getResponse().getContentAsString();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

}
