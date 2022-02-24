package com.neuralgalaxy.commons.visitor;

/**
 * jwt id 存储地址，用于强制下线操作
 *
 * @author <a href="mailto:ni@renzhen.la">haiker</a>
 * @version 20220221
 */
public interface VisitorStorage {

    /**
     * 保存 token 内容
     *
     * @param visitor 访问者
     * @param keyId   此次生成的keyId
     * @param token   生成的token值
     */
    void save(Visitor visitor, String keyId, String token);

    /**
     * 存储验证，如果验证成功可以继续，如果本地验证不存在就是失败
     *
     * @param visitor 访问者
     * @param keyId   keyId
     * @return 验证是否成功
     */
    boolean verify(Visitor visitor, String keyId);

    /**
     * 清理用户token
     *
     * @param visitor 访问者
     * @param keyId   keyId
     */
    void clean(Visitor visitor, String keyId);
}
