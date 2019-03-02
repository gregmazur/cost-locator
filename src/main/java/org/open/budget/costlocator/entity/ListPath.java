package org.open.budget.costlocator.entity;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

//Temporary
@Entity
@Table(name = "list_path")
@Getter
@Builder
public class ListPath {
    @Id
    @Column(name = "id")
    private Long id;
    @Column(name = "last_path")
    private String lastPath;

    public ListPath() {
    }

    public ListPath(Long id, String lastPath) {
        this.id = id;
        this.lastPath = lastPath;
    }
}
