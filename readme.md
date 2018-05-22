# 订单支付系统

## 接口说明

创建订单

    POST /order/create
    
    bodyJson
    {
      "userId": "xxxxdsjson",
      "orderType": "3",
      "eventId": "test",
      "items": [
        {
          "itemId": "item13",
          "price": 200,
          "count": 1
        },
        {
          "itemId": "item23",
          "price": 100,
          "count": 2
        },
        {
          "itemId": "item33",
          "price": 300,
          "count": 3
        }
      ]
    }
    
    
发起支付

    POST /order/pay
    
    bodyJson
    {
          "agent": "alipay",
          "orderId": "1483764928434l7cf235",
    }
    
客户端支付后回调地址

    POST /order/unsettled/{orderId}
    
    bodyJson
    {
              "orderId": "1483764928434l7cf235"
    }
    
ping++支付回调地址

    POST /order/callback
   
查询接口

1.按照状态查询

	/order/get/orderState

2.安装类型查询

	/order/get/type
	
3.按用户id查询

	/order/get/userId
	
4.按照时间区间查询
	
	/order/get/date