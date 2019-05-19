package me.chen.core.resolver;

import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author: ftdcs
 * @Date: 2019/05/19 0019 18:19
 * @Version 1.0
 */
@Slf4j
public class DateResolver implements Resolver {

    @Override
    public Object resolver(Class type, String value) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = sdf.parse(value);
            return date;
        } catch (ParseException e) {
            log.error(e.getMessage(),e);
        }
        return null;
    }
}
