package com.mountBlue.canduitBoot.DAO;

import com.mountBlue.canduitBoot.entity.Tags;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagsRepository extends JpaRepository<Tags, Integer> {

    public Tags findByName(String tagName);
}
