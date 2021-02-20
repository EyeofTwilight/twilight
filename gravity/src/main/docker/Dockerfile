# 指定基础镜像 这里springboot项目运行只需要java jdk环境即可
FROM java:8
# 维护者信息
MAINTAINER zq
# 将本地的可执行文件拷贝到Docker容器中的根目录下
COPY app.sh /
#给app.sh赋予可执行权限
RUN chmod +x /app.sh
# 对当前目录下jar包,以app.jar的名称放入docker容器根目录
ADD *.jar app.jar
# 对外暴漏的端口号
EXPOSE 8191
# 运行-开机启动
# 方式一
ENTRYPOINT ["/app.sh"]
#ENTRYPOINT ["java", "-jar", "app.jar"]  # 方式二