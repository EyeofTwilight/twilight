# **smart doc maven plugin使用记录**

smart-doc官方wiki: https://gitee.com/smart-doc-team/smart-doc/wikis/Home?sort_id=1652800

Dubbo接口生成官网文档： https://gitee.com/smart-doc-team/smart-doc/wikis/dubbo api文档生成?sort_id=2215419

### Dubbo接口生成文档注意
无须指定packageFilters配置项,并且,dubbo client的源码是必须同时打包的

            <!-- Source -->
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-source-plugin</artifactId>
                <version>3.0.1</version>
                <executions>
                    <execution>
                        <phase>package</phase>
                        <goals>
                            <goal>jar-no-fork</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
#### 无私服 本地生成
只maven install dubbo client是没用的，服务提供者是获取不到clien中的对象的
需要引入client时同时引入它的source code

        <!-- duboo-interface -->
        <dependency>
            <groupId>com.wangshen</groupId>
            <artifactId>duboo-interface</artifactId>
            <version>0.0.1-SNAPSHOT</version>
        </dependency>
        <dependency>
            <groupId>com.wangshen</groupId>
            <artifactId>duboo-interface</artifactId>
            <version>0.0.1-SNAPSHOT</version>
            // 引入源码
            <classifier>sources</classifier>
        </dependency>
#### 有私服
直接maven deploy即可