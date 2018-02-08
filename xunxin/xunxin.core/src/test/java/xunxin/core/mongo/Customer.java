package xunxin.core.mongo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document   //文档
public class Customer{

    @Id
    @Indexed(unique=true)
    private String id;

    @Field("name")//MongoDB中的key
    private String name; //将name转换成为Json类型的Customer_name

    //作为值转换，**由于mongoDb中的一个键也是"password"和它一样的字符串，所以不用写@Field("password")注解
    private String password;


    /**
     *提供set/get和toString方法
     */
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }  


}