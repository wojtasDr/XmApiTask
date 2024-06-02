package xm.springconfig;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@PropertySource("application.properties")
@ComponentScan("xm")
public class Config {
}
