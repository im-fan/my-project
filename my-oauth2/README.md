### SpringCloud + Oauth2

- [参考链接](https://github.com/lexburner/oauth2-demo)
- [OAuth2 RFC6749中文翻译](https://colobu.com/2017/04/28/oauth2-rfc6749/)
- [理解OAuth 2.0-阮一峰](http://www.ruanyifeng.com/blog/2014/05/oauth_2_0.html)

#### 授权模式
- 授权码模式（authorization code）
```text
功能最完整、流程最严密的授权模式。它的特点就是通过客户端的后台服务器，与"服务提供商"的认证服务器进行互动。
```
- 简化模式（implicit）

- 密码模式（resource owner password credentials）
- 客户端模式（client credentials）

- 主要配置
```text
Oauth2ServerConfig
WebSecurityConfigurer
```

#### 不同授权模式请求
- 授权码模式(在浏览器中访问接口)
```text
配置项：需要将返回地址添加到client中
    clients.redirectUris("http://www.baidu.com")

http://localhost:8200/oauth/authorize?response_type=code&client_id=client_1&redirect_uri=http://www.baidu.com&state=123

所需参数解释
    response_type：表示授权类型，必选项，此处的值固定为"code"
    client_id：表示客户端的ID，必选项
    redirect_uri：表示重定向URI，可选项
    scope：表示申请的权限范围，可选项
    state：表示客户端的当前状态，可以指定任意值，认证服务器会原封不动地返回这个值。

```

- 简化模式(在浏览器中访问接口)
```text
http://localhost:8200/oauth/authorize?response_type=token&client_id=client_1&redirect_uri=http://www.baidu.com&state=123&scope=select

参数解释：
    response_type：表示授权类型，此处的值固定为"token"，必选项。
    client_id：表示客户端的ID，必选项。
    redirect_uri：表示重定向的URI，可选项。
    scope：表示权限范围，可选项。
    state：表示客户端的当前状态，可以指定任意值，认证服务器会原封不动地返回这个值。
```

- password方式获取toke
```text
POST http://localhost:8200/oauth/token?grant_type=password&scope=select&client_id=client_1&client_secret=123456&username=user_1&password=123456

{
    "access_token": "39be5ea6-fdcd-4b15-a4dd-1f3dbaf8fc63",
    "token_type": "bearer",
    "refresh_token": "396e6c5e-9d79-420a-8b25-945098b10c82",
    "expires_in": 43021,
    "scope": "select"
}

参数解释
    grant_type：表示授权类型，此处的值固定为"password"，必选项。
    username：表示用户名，必选项。
    password：表示用户的密码，必选项。
    scope：表示权限范围，可选项。

```

- client方式获取access_token
```text
POST http://localhost:8200/oauth/token?grant_type=client_credentials&scope=select&client_id=client_2&client_secret=123456

{
    "access_token": "17fc17a9-83b2-41c3-8621-c727d8329bbd",
    "token_type": "bearer",
    "expires_in": 42400,
    "scope": "select"
}

参数解释
    granttype：表示授权类型，此处的值固定为"clientcredentials"，必选项。
    scope：表示权限范围，可选项。

```


- 刷新token
```text
POST http://localhost:8200/oauth/token?grant_type=refresh_token&refresh_token=396e6c5e-9d79-420a-8b25-945098b10c82&client_id=client_2&client_secret=123456

{
    "access_token": "e0e64627-f157-4718-81f0-069ca21549ad",
    "token_type": "bearer",
    "refresh_token": "396e6c5e-9d79-420a-8b25-945098b10c82",
    "expires_in": 43199,
    "scope": "select"
}

```

#### 请求业务接口
- 请求接口
```text
配置拦截：
    HttpSecurity中配置 http.antMatchers("/user/**").authenticated()

使用client方式获取的access_token
GET http://localhost:8200/user/info?access_token=d8f47460-c0a6-4247-9f87-1712bae5325e

返回结果
```
