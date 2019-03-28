package com.hansholdings.manage.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hansholdings.basic.dao.IBaseDao;
import com.hansholdings.basic.log4j.Logger;
import com.hansholdings.basic.service.impl.BaseServiceImpl;
import com.hansholdings.basic.utils.MyExcelWriter;
import com.hansholdings.manage.dao.IOperationDao;
import com.hansholdings.manage.entity.Operationinfo;
import com.hansholdings.manage.entity.QueryCondition;
import com.hansholdings.manage.service.IOperationService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 *
 * @author maozz11347
 * @date 2017/12/21
 */
@Service
public class OperationServiceImpl extends BaseServiceImpl<Operationinfo, Long> implements IOperationService {

    private static Logger logger = Logger.getLogger(LogininfoServiceImpl.class);

    @Value("${opLogObject}")
    private String opLogObject;

    @Autowired
    private IOperationDao operationDao;
    @Override
    public IBaseDao<Operationinfo, Long> getBaseDao() {
        return this.operationDao;
    }

    @Override
    public Page<Operationinfo> searchByPage(QueryCondition query) {
        return operationDao.findAll(getSpecification(query), new PageRequest(query.getStart()/query.getLength(),query.getLength(),new Sort(Sort.Direction.DESC,"id")));
    }

    @Override
    public List<Operationinfo> searchAll(QueryCondition query) {
        return operationDao.findAll(getSpecification(query),new Sort(Sort.Direction.DESC,"id"));
    }

    @Override
    public Operationinfo searchByUuid(String uuid) {
        return operationDao.findByUuid(uuid);
    }

    @Override
    public JSONObject exportOpLog(QueryCondition query) {
        JSONObject result;
        List<Operationinfo> ops=searchAll(query);
        MyExcelWriter writer =new MyExcelWriter();
        String postad = UUID.randomUUID().toString().replaceAll("-", "");
        String filepathString =System.getProperty("user.dir").replace("\\","/").replace("bin","webapps/ROOT/WEB-INF/classes/static")+"/exportFile/"+postad+".xls";
       // String filepathString ="D:\\Workspace\\IntellijIDEA\\adusermanage\\src\\main\\resources\\static\\exportFile\\"+postad+".xls";
        System.out.println(filepathString);
        try {
            writer.write2Excel(Arrays.asList(opLogObject.split(",")), JSONArray.parseArray(JSON.toJSONString(ops)), filepathString);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("export Exception",e,ops);
            e.printStackTrace();

        }
        result=JSON.parseObject("{code:'200',desp:'共有"+ops.size()+"条数据，',exportFnm:'"+postad+".xls'}");

        return result;
    }


    public  Specification<Operationinfo> getSpecification(QueryCondition query){
        Specification<Operationinfo> specification=new Specification<Operationinfo>() {
            @Override
            public Predicate toPredicate(Root<Operationinfo> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicate = new ArrayList<>();
                try {
                    if (null != query && !StringUtils.isEmpty(query.getMinOperationTime())) {
                        // 这里也可以root.get("name").as(String.class)这种方式来强转泛型类型
                        predicate.add(criteriaBuilder.greaterThanOrEqualTo(root.<Date>get("operationTime"), new SimpleDateFormat("yyyy-MM-dd").parse(query.getMinOperationTime())));
                    }
                    if (null != query && !StringUtils.isEmpty(query.getMaxOperationTime())) {
                        predicate.add(criteriaBuilder.lessThanOrEqualTo(root.<Date>get("operationTime"), new SimpleDateFormat("yyyy-MM-dd").parse(query.getMaxOperationTime())));

                    }
                    if (null != query && !StringUtils.isEmpty(query.getOperateStype())) {
                        predicate.add(criteriaBuilder.equal(root.<String>get("operateStype"), query.getOperateStype()));

                    }
                    if (null != query && !StringUtils.isEmpty(query.getServiceStype())) {
                        predicate.add(criteriaBuilder.equal(root.<String>get("serviceStype"), query.getServiceStype()));

                    }
                    if (null != query && !StringUtils.isEmpty(query.getUserName())) {
                        predicate.add(criteriaBuilder.like(root.<String>get("userName"), "%" + query.getUserName() + "%"));

                    }
                } catch (ParseException e) {
                    logger.info("日期参数格式错误",e.getMessage());
                }
                return criteriaBuilder.and(predicate.toArray(new Predicate[predicate.size()]));
            }
        };
        return specification;
    }
}
