package com.se128.jupiter.dao.daoImpl;

import com.se128.jupiter.dao.UserDao;
import com.se128.jupiter.entity.Order;
import com.se128.jupiter.entity.User;
import com.se128.jupiter.repository.GoodsRepository;
import com.se128.jupiter.repository.OrderRepository;
import com.se128.jupiter.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    UserRepository userRepository;
    @Autowired
    GoodsRepository goodsRepository;

    @Override
    public User getUserByUserId(Integer userId){
        return userRepository.getUserByUserId(userId);
    }

    @Override
    public User getUserByUsernameAndPassword(String username, String password)
    {
        return userRepository.getUserByUsernameAndPassword(username,password);
    }

    @Override
    public User addUser(User user)
    {
        User user1 = userRepository.getUserByUsername(user.getUsername());
        if(user1 == null)
        {
            return userRepository.saveAndFlush(user);
        }
        else {
            return null;
        }
    }

    @Override
    public List<Order> getOrdersByUserId(Integer userId)
    {
        User user = userRepository.getUserByUserId(userId);
        return user.getOrders();
    }
}
