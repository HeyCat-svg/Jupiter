import React from 'react';
import {Link} from 'react-router-dom';
import {UserOutlined} from '@ant-design/icons';
import {checkSession} from "../services/userService";
import {Menu, Dropdown, message} from 'antd';
import '../css/header.css';
import {SearchBar} from "./SearchBar";
import {logout} from "../services/userService";

export class Header extends React.Component{
    constructor(props) {
        super(props);
        this.state={loggedIn:false,user:null};
    }

    componentDidMount() {
        const callback = (data) => {
            if(data.status === 0){
                this.setState(
                    {
                        loggedIn:true,
                        user:data.data
                    }
                )
            }
            else{
                this.setState(
                    {
                        loggedIn:false,
                        user:null
                    }
                )
            }
        };
        checkSession(callback);
    }

    logout = () => {
        console.log("Logout");
        const callback = (data) => {
            sessionStorage.removeItem("user");
            this.setState(
                {
                    loggedIn:false,
                    user:null
                }
            );
            message.success(data.msg);
        };
        logout(callback);
    }

    menu = (
        <Menu>
            <Menu.Item>
                <Link to={{pathname: '/personalInfo'}}>
                    个人信息
                </Link>
            </Menu.Item>
            <Menu.Item>
                <Link to={{pathname: '/personalInfo'}}>
                    账号设置
                </Link>
            </Menu.Item>
            <Menu.Item>
                <Link to={{pathname: '/orderList'}}>
                    查看订单
                </Link>
            </Menu.Item>
            <Menu.Item>
                <div onClick={this.logout}>
                    退出登录
                </div>
            </Menu.Item>
        </Menu>
    );

    adminMenu = (
        <Menu>
            <Menu.Item>
                <Link to={{pathname: '/personalInfo'}}>
                    个人信息
                </Link>
            </Menu.Item>
            <Menu.Item>
                <Link to={{pathname: '/personalInfo'}}>
                    账号设置
                </Link>
            </Menu.Item>
            <Menu.Item>
                <Link to={{pathname: '/orderList'}}>
                    查看订单
                </Link>
            </Menu.Item>
            <Menu.Item>
                <Link to={{pathname: '/userList'}}>
                    用户管理
                </Link>
            </Menu.Item>
            <Menu.Item>
                <Link to={{pathname: '/adminOrderList'}}>
                    系统订单
                </Link>
            </Menu.Item>
            <Menu.Item>
                <div onClick={this.logout}>
                    退出登录
                </div>
            </Menu.Item>
        </Menu>
    );



    render() {
        return(
            <div className="header">
                <Link to={{pathname:'/'}}>
                    <div className="logo">
                        <img src={[require("../assets/planet.png")]} width={80} height={80} alt="Website logo"/>
                        {/*<img src={[require("../assets/logo2.png")]} width={200} height={80} alt="Website logo"/>*/}
                    </div>
                </Link>
                <div className="navigationList">
                    <Link to={{pathname:'/'}}>
                        <li className="navigation">
                            首页
                        </li>
                    </Link>
                    <Link to={{pathname:'/goodsList'}}>
                        <li className="navigation">
                            分类
                        </li>
                    </Link>

                </div>
                <div className="searchBarContainer">
                    <SearchBar/>
                </div>
                <div className="auth">
                    {
                        this.state.loggedIn ?
                            (
                                this.state.user.userType === 0 ?
                                    (
                                        <li className="headerList">
                                            <Dropdown overlay={this.adminMenu}>
                                                <div>
                                                    <UserOutlined/>{this.state.user.username}
                                                </div>
                                            </Dropdown>
                                        </li>
                                    ):
                                    (
                                        <li className="headerList">
                                            <Dropdown overlay={this.menu}>
                                                <div>
                                                    <UserOutlined/>{this.state.user.username}
                                                </div>
                                            </Dropdown>
                                        </li>
                                    )

                            ):
                            (
                                <li className="headerList">
                                    <Link to={{pathname:'/login'}}>
                                        <UserOutlined />登录
                                    </Link>
                                </li>
                            )
                    }
                </div>
            </div>
        );
    }
}
