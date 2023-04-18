package com.gm.sn.service;

import com.gm.sn.dao.AuditStatusDAO;
import com.gm.sn.dao.CategoryDAO;
import com.gm.sn.entity.Audit_status;
import com.gm.sn.entity.Category;
import com.gm.sn.redis.RedisService;
import com.gm.sn.util.CastUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Evan
 * @date 2019/4
 */
@Service
public class AuditStatusService {
    @Autowired
    AuditStatusDAO auditStatusDAO;
    @Autowired
    private RedisService redisService;

    public List<Audit_status> list() {
        Sort sort = new Sort(Sort.Direction.ASC, "id");
        return auditStatusDAO.findAll(sort);
    }

    public Audit_status get(int id) {
        Audit_status c= auditStatusDAO.findById(id).orElse(null);
        return c;
    }

    public void addOrUpdate(Audit_status audit_status) {
        redisService.delete("auditStatuslist");
        auditStatusDAO.save(audit_status);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        redisService.delete("auditStatuslist");
    }

    public void deleteById(int id) {
        redisService.delete("auditStatuslist");
        auditStatusDAO.deleteById(id);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        redisService.delete("auditStatuslist");
    }
}
