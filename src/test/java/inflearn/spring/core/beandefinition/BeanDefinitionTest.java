package inflearn.spring.core.beandefinition;

import inflearn.spring.core.AppConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class BeanDefinitionTest {
    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
//    GenericXmlApplicationContext ac = new GenericXmlApplicationContext("appConfig.xml");

    /*
    * XmlApplicationContext와 AnnotationConfigApplicationContext를 이용해 등록한 BeanDefinition 정보는 약간 다르다.
    *  - XmlApplicationContext의 BeanDefinition은 factoryBeanName, factoryMethodName이 null
    *  - AnnotationConfigApplicationContext의 BeanDefinition은 factoryBeanName=설정정보 class명, factoryMethodName=Bean이름으로 등록이 되어있다.
    * 이유 :
    * Xml방식은 Bean 자체를 등록하는 것이고,
    * Annotation 방식은 설정정보를 입력한 class의 안에 내가 작성한 메서드를 호출하여
    * 우회(?)하여 등록하는 것이기 때문이다.
    *
     * */
    @Test
    @DisplayName("빈 설정 메타정보 확인")
    void findApplicationBean(){
        String[] beanDefinitionNames = ac.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            BeanDefinition beanDefinition = ac.getBeanDefinition(beanDefinitionName);

            if(beanDefinition.getRole() == BeanDefinition.ROLE_APPLICATION){
                System.out.println("beanDefinitionName = " + beanDefinitionName +
                        " beanDefinition = " + beanDefinition);
            }
        }
    }
}
