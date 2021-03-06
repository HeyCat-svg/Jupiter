package com.se128.jupiter.controller;

import com.se128.jupiter.entity.Order;
import com.se128.jupiter.service.OrderService;
import com.se128.jupiter.util.logutils.LogUtil;
import com.se128.jupiter.util.msgutils.Msg;
import com.se128.jupiter.util.msgutils.MsgCode;
import com.se128.jupiter.util.msgutils.MsgUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@RestController
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @RequestMapping("/addOrder")
    public Msg addOrder(@RequestBody Map<String, String> params) {

        LogUtil.info("addOrder");

        Order order = new Order();

        Integer userId = Integer.valueOf(params.get("userId"));
        Integer number = Integer.valueOf(params.get("number"));
        Integer detailId = Integer.valueOf(params.get("detailId"));

        System.out.println("userId:" + userId.toString());
        System.out.println("number:" + number.toString());
        System.out.println("detailId:" + detailId.toString());

        order.setUserId(userId);
        order.setNumber(number);

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        order.setTime(timestamp.toString());

        order.setSourceId(54749110);

        Order order1 = orderService.addOrder(order, detailId);

        if (order1 == null) {
            return MsgUtil.makeMsg(MsgCode.ADD_ERROR, MsgUtil.BUY_ERROR_MSG);
        }

        JSONObject data = JSONObject.fromObject(order1);
        return MsgUtil.makeMsg(MsgCode.ADD_SUCCESS, MsgUtil.BUY_SUCCESS_MSG, data);
    }

    @RequestMapping("/getAllOrders")
    public Msg getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        JSONObject data = new JSONObject();
        JSONArray orderList = JSONArray.fromObject(orders);
        data.put("orders", orderList.toString());
        return MsgUtil.makeMsg(MsgCode.DATA_SUCCESS, data);
    }
}



