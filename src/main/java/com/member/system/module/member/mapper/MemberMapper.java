package com.member.system.module.member.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.member.system.module.member.entity.Member;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * 会员 Mapper
 */
@Mapper
public interface MemberMapper extends BaseMapper<Member> {

    @Select("SELECT * FROM member WHERE username = #{username} AND deleted = 0 LIMIT 1")
    Member findByUsername(@Param("username") String username);

    @Select("SELECT * FROM member WHERE mobile = #{mobile} AND deleted = 0 LIMIT 1")
    Member findByMobile(@Param("mobile") String mobile);

    @Update("UPDATE member SET points = points + #{delta}, " +
            "total_points = CASE WHEN #{delta} > 0 THEN total_points + #{delta} ELSE total_points END, " +
            "updated_at = NOW() WHERE id = #{memberId} AND deleted = 0")
    int updatePoints(@Param("memberId") Long memberId, @Param("delta") int delta);
}
