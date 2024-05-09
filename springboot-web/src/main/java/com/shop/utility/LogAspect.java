package com.shop.utility;

import com.shop.entity.OperationLog;
import com.shop.entity.Result;
import com.shop.service.LogService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.ibatis.jdbc.Null;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

@Component
@Aspect
//处理AOP的处理器
public class LogAspect {
    @Autowired
    private LogService logService;

    @Around("@annotation(autoLog)")
    public Object doAround(ProceedingJoinPoint joinPoint,AutoLog autoLog)throws Throwable{
        try {
            //执行具体的接口
            Result result=(Result) joinPoint.proceed();

            //操作内容
//            String operate=autoLog.operate();
//
//            //操作者身份
//            String identify=autoLog.identify();
//
//            //操作时间
//            Date time=new Date();
//            Timestamp operatetime = new Timestamp(time.getTime());
//
//            HttpServletRequest request= ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
//            //操作ip
//            String ip=getIp(request);
//
//            //操作姓名
//            String userid="";
//            Claims claims = null;
//            String token = request.getHeader("token");
//            if(token!=null)
//            {
//                claims=JwtUtils.parseJWT(token);
//                userid=claims.get("username", String.class);
//            }
//            else
//            {
//                token= (String) result.getData();
//                claims=JwtUtils.parseJWT(token);
//                userid=claims.get("username", String.class);
//            }
//
//            OperationLog operationLog=new OperationLog(null,userid,identify,operate,ip,operatetime);
//            logService.addlog(operationLog);

            return result;
        }
        catch (Exception error)
        {
            System.out.println(error);
            return Result.error("日志信息添加失败");
        }
    }

    private String getIp(HttpServletRequest request) {
        List<String> ipHeadList = Stream.of("X-Forwarded-For", "Proxy-Client-IP", "WL-Proxy-Client-IP", "HTTP_CLIENT_IP", "X-Real-IP").toList();
        for (String ipHead : ipHeadList) {
            if (checkIP(request.getHeader(ipHead))) {
                return request.getHeader(ipHead).split(",")[0];
            }
        }
        return "0:0:0:0:0:0:0:1".equals(request.getRemoteAddr()) ? "127.0.0.1" : request.getRemoteAddr();
    }

    private boolean checkIP(String ip) {
        return !(null == ip || ip.isEmpty() || "unknown".equalsIgnoreCase(ip));
    }
}
