package com.dinethbakers.hrm.service.impl;

import com.dinethbakers.hrm.entity.BranchEntity;
import com.dinethbakers.hrm.model.Branch;
import com.dinethbakers.hrm.repository.jparepository.BranchRepository;
import com.dinethbakers.hrm.service.BranchService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BranchServiceImpl implements BranchService {

    private final BranchRepository branchJpaRepository;
    private final ObjectMapper mapper;

    @Override
    public Branch persist(Branch branch) {
        BranchEntity savedEntity = branchJpaRepository.save(mapper.convertValue(branch, BranchEntity.class));

        return mapper.convertValue(savedEntity, Branch.class);
    }

    @Override
    public Branch getById(Integer id) {
        Optional<BranchEntity> byId = branchJpaRepository.findById(id);

        return mapper.convertValue(byId, Branch.class);
    }

    @Override
    public Branch getByName(String name) {
        Optional<BranchEntity> byName = branchJpaRepository.findByName(name);

        if (byName.isEmpty())
            return null;

        return mapper.convertValue(byName,Branch.class);
    }

    @Override
    public List<Branch> getAll() {
        Iterable<BranchEntity> branchList = branchJpaRepository.findAll();

        List<Branch> branches = new ArrayList<>();

        branchList.forEach(branchEntity ->
                branches.add(mapper.convertValue(branchEntity, Branch.class)));

        return branches;
    }

    @Override
    public Branch update(Branch branch) {
        BranchEntity branchEntity = mapper.convertValue(branch, BranchEntity.class);
        //branchEntity.setBranchId(branch.getBranchId());
        BranchEntity savedEntity = branchJpaRepository.save(branchEntity);
        return mapper.convertValue(savedEntity, Branch.class);
    }

    @Override
    public Boolean delete(Integer id) {
        Optional<BranchEntity> byId = branchJpaRepository.findById(id);

        if (byId.isPresent()){
            branchJpaRepository.delete(mapper.convertValue(byId, BranchEntity.class));
            return true;
        }

        return false;
    }

    @Override
    public List<String> getAllNames() {
        return branchJpaRepository.findAllNames();
    }


}
