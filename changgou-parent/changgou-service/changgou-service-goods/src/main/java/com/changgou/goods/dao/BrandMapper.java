package com.changgou.goods.dao;
import com.changgou.goods.pojo.Brand;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/****
 * @Author:admin
 * @Description:Brand的Dao
 * @Date 2019/6/14 0:12
 *****/
public interface BrandMapper extends Mapper<Brand> {

    /**
     * 根据分类id查询品牌集合
     * @param categoryid
     * @return
     */
    @Select("SELECT tb.* FROM tb_brand tb,tb_category_brand tcb WHERE tb.id = tcb.brand_id and tcb.category_id = #{categoryid}")
    List<Brand> findByCategory(Integer categoryid);

}
