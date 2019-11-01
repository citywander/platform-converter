package com.sfsf.platform.common.fastmap.cust;

import java.beans.IntrospectionException;

import org.junit.Test;

import com.sfsf.platform.common.fastmap.MapperClassGenerator;
import com.sfsf.platform.common.fastmap.bean.UserBO;

import javassist.CannotCompileException;

public class MapperClassGeneratorTest {
    
    @Test
    public void testCreateClass() throws InstantiationException, IllegalAccessException, IntrospectionException {
        MapperClassGenerator generator = new MapperClassGenerator();
        try {
            UserBO source = new UserBO();
            source.setId("1");
            source.setName("name");
            Class<UserMapper> stm = generator.generate(UserMapper.class);
            UserMapper st = (UserMapper)stm.newInstance();
            st.toDTO(source);
            
        } catch (CannotCompileException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
