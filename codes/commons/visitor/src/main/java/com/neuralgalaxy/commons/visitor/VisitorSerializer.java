package com.neuralgalaxy.commons.visitor;

/**
 * @author haiker <a href="mailto:wo@renzhen.la">wo@renzhen.la</a>
 * @version 1.0 &amp; 2019-06-24 15:28
 */
public interface VisitorSerializer {

    /**
     * 将访问者编译成访问票根
     *
     * @param visitor 访问者
     * @return header中访问者票根
     */
    String encode(Visitor visitor);

    /**
     * 从访问票根中获取访问者信息
     *
     * @param ticket ticket value
     * @return visitor object
     */
    Visitor decode(String ticket);
}
