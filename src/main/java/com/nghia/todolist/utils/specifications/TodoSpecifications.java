package com.nghia.todolist.utils.specifications;

import com.nghia.todolist.entity.TodoEntity;
import com.nghia.todolist.utils.enums.TodoStatus;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TodoSpecifications {
    public static Specification<TodoEntity> filterTodos(String title, Date startDate, Date endDate, Long userId, TodoStatus status) {

        return (root, query, criteriaBuilder) -> {

            List<Predicate> predicates = new ArrayList<>();

            // 1. Lọc theo Title (LIKE)
            if (StringUtils.hasText(title)) {
                predicates.add(criteriaBuilder.like(
                        root.get("title"), "%" + title + "%"));
            }

            // 2. Lọc theo Khoảng thời gian (Date Range)
            if (startDate != null) {
                // timeAt >= startDate
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(
                        root.get("timeAt"), startDate));
            }

            if (endDate != null) {
                // timeAt <= endDate
                predicates.add(criteriaBuilder.lessThanOrEqualTo(
                        root.get("timeAt"), endDate));
            }

            // 3. Lọc theo User ID (EQUAL)
            if (userId != null) {
                predicates.add(criteriaBuilder.equal(
                        root.get("idUser"), userId));
            }

            ///  STATUS
            if (status != null) {
                // timeAt >= startDate
                predicates.add(criteriaBuilder.equal(
                        root.get("todoStatus"), status));
            }
            predicates.add(criteriaBuilder.equal(
                    root.get("isRemoved"), false));
            // Kết hợp TẤT CẢ các điều kiện đã thêm bằng toán tử AND
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
