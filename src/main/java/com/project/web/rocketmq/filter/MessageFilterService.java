package com.project.web.rocketmq.filter;

import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.common.filter.FilterContext;
import org.apache.rocketmq.common.filter.MessageFilter;
import org.apache.rocketmq.common.message.MessageExt;

/**
 * 消息过滤，使用messageFilter方式
 *
 *@author: Weiyf
 *@Date: 2019-07-03 15:32
 */
public class MessageFilterService implements MessageFilter {

    @Override
    public boolean match(MessageExt msg, FilterContext context) {
        String idStr = msg.getProperty("id");
        if(StringUtils.isEmpty(idStr)) {
            return false;
        }
        int id = Integer.valueOf(idStr);
        if(id % 2 == 0 && id % 3 == 0){
            return true;
        }
        return false;
    }
}
