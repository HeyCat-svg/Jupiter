import React from 'react';
import {Header} from "../components/Header";
import {DetailCard} from "../components/DetailCard";
import{DetailShowTab} from "../components/DetailShowTab";
import{Recommendation} from "../components/Recommendation";
import {Row, Col, Card, Tabs, message, BackTop} from 'antd';
import {getGoodsByGoodsId} from "../services/goodsService";
import {checkSession} from "../services/userService";
import {logout} from "../services/userService";
import "../css/detailview.css";

let tmpId = null;
let goodsData = null;
export class DetailView extends React.Component {
    constructor(props) {
        super(props);
        this.state={
            goodsId:null,
            goodsData:null,
            loggedIn:false,
            user:null
        };
    }
    componentDidMount() {
        const query = this.props.location.search;
        const arr = query.split('?');
        tmpId = arr[1].substr(3);
        this.setState({goodsId:tmpId});

        const callback = (data) =>{
            goodsData = data.data;
            this.setState({goodsData:data.data});
        }
        if(tmpId === null){
            return;
        }
        const requestData = {goodsId:tmpId};
        getGoodsByGoodsId(requestData,callback);
        const checkSession_callback = (data) => {
            if(data.status === 0){
                this.setState(
                    {
                        loggedIn:true,
                        user:data.data
                    }
                )
            }
        };
        checkSession(checkSession_callback);
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

    render(){
        console.log('goodsID',this.state.goodsId)
        if(tmpId === null)
            return null;
        if(goodsData === null)
            return null;
        return(
            <Row align="top" gutter={16}>
                <Header
                    loggedIn={this.state.loggedIn}
                    user={this.state.user}
                    logout={this.logout}
                />
                <DetailCard info={tmpId} />
                <Row align = "top" gutter={16}>
                    <Col span={16}>
                        <DetailShowTab info={goodsData}/>
                    </Col>
                    <Col className={"recommend"}>
                        <Recommendation
                            loggedIn={this.state.loggedIn}
                            user={this.state.user}
                        />
                    </Col>
                </Row>
                <BackTop/>
            </Row>
        );
    }
}
