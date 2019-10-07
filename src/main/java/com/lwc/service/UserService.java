package com.lwc.service;

import com.lwc.common.Constant;
import com.lwc.common.Pager;
import com.lwc.common.Respon;
import com.lwc.dao.UserDao;
import com.lwc.entity.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2019/9/5.
 */
@Service
public class UserService {
    @Autowired
    private UserDao userDao;

    public Respon list(Pager pager) {
        Respon respon = new Respon();
        try {
            Pageable pageable = new PageRequest(pager.getCurrentPage()-1,pager.getPageSize());
            Specification<User> spec = new Specification<User>() {
                @Override
                public Predicate toPredicate(Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                    List<Predicate> list = new ArrayList<>();
                    if(StringUtils.isNotBlank(pager.getKeyword())){
                        Predicate p = cb.like(root.get("loginName").as(String.class), "%" + pager.getKeyword() + "%");
                        list.add(p);
                    }
                    return cb.and(list.toArray(new Predicate[list.size()]));
                }
            };
            Page<User> page = userDao.findAll(spec, pageable);
            List<User> list = page.getContent();
            Long totalElements = page.getTotalElements();
            int totalCount = totalElements.intValue();
            int pageCount = (totalCount+pager.getPageSize()-1)/pager.getPageSize();
            pager.setList(list);
            pager.setTotalCount(totalCount);
            pager.setPageCount(pageCount);
            respon.setData(pager);
            respon.setCode(Constant.CODE_SUCCESS);
            respon.setMsg(Constant.CODE_SUCCESS_STRING);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return respon;
    }

    public Respon delete(Long id) {
        Respon respon = new Respon();
        try {
            userDao.deleteById(id);
            respon.setCode(Constant.CODE_SUCCESS);
            respon.setMsg(Constant.CODE_SUCCESS_STRING);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return respon;
    }

    public Respon saveOrUpdate(Pager pager) {
        Respon respon = new Respon();
        if(pager.getId()>0){
            //更新
            User user = userDao.findById(pager.getId()).get();
            user.setName(pager.getName());
            user.setLoginName(pager.getLoginName());
            user.setPassword(DigestUtils.md5DigestAsHex(pager.getPassword().getBytes()));
            user.setiDcard(pager.getiDcard());
            user.setSex(pager.getSex());
            userDao.save(user);
            respon.setCode(Constant.CODE_SUCCESS);
            respon.setMsg("更新成功！");
        }else{
            User user = new User();
            user.setName(pager.getName());
            user.setLoginName(pager.getLoginName());
            user.setPassword(DigestUtils.md5DigestAsHex(pager.getPassword().getBytes()));
            user.setiDcard(pager.getiDcard());
            user.setSex(pager.getSex());
            userDao.save(user);
            respon.setCode(Constant.CODE_SUCCESS);
            respon.setMsg("保存成功！");
        }
        return respon;
    }
}
