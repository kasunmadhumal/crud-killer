package com.crudkiller;

import com.crudkiller.config.CrudKillerAutoConfiguration;
import com.crudkiller.util.CommonUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

import static org.assertj.core.api.Assertions.assertThat;

class CrudKillerTest {

    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
            .withConfiguration(AutoConfigurations.of(CrudKillerAutoConfiguration.class));

    @Test
    void contextLoads() {
        contextRunner.run(context -> {
            assertThat(context).hasSingleBean(CrudKillerAutoConfiguration.class);
        });
    }

    @Test
    void commonUtilityMethods() {

        assertThat(CommonUtil.isEmpty("")).isTrue();
        assertThat(CommonUtil.isNotEmpty("test")).isTrue();
        assertThat(CommonUtil.nvl(null, "default")).isEqualTo("default");
    }
}