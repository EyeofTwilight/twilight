# observer 观察者模式
## 需求
    用户在订单付完款后，需要
    1.发送微信消息提醒，如：有关订单的详细信息
    2.获取一次抽奖机会
    3.发送短信，如：购买成功
    
## 实现
    1.JDK中自带java.util.Observable   java.util.Observer 
        但Observable中的方法为synchronized修饰
        会导致如:
            订单完成后，需要等待其后的operation完成才能进行下一步;
            或者其中一个operation报错，导致其后的动作无法执行;
    2.自己按照JDK的思路实现一个异步的观察者模式