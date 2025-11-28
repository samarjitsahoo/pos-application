package app.posify.service.impl;

import app.posify.exceptions.UserException;
import app.posify.mapper.BranchMapper;
import app.posify.modal.Branch;
import app.posify.modal.Store;
import app.posify.modal.User;
import app.posify.payload.dto.BranchDTO;
import app.posify.repository.BranchRepository;
import app.posify.repository.StoreRepository;
import app.posify.repository.UserRepository;
import app.posify.service.BranchService;
import app.posify.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BranchServiceImpl implements BranchService {

    private final BranchRepository branchRepository;
    private final StoreRepository storeRepository;
    private final UserService userService;

    @Override
    public BranchDTO createBranch(BranchDTO branchDTO) throws UserException {
        User currentUser = userService.getCurrentUser();
        Store store = storeRepository.findByStoreAdminId(currentUser.getId());

        Branch branch = BranchMapper.toEntity(branchDTO, store);
        Branch savedBranch = branchRepository.save(branch);
        return BranchMapper.toDTO(savedBranch);
    }

    @Override
    public BranchDTO updateBranch(Long id, BranchDTO branchDTO) throws Exception {
        Branch existing =  branchRepository.findById(id).orElseThrow(
                () -> new Exception("branch not exist...")
        );
        existing.setName(branchDTO.getName());
        existing.setWorkingDays(branchDTO.getWorkingDays());
        existing.setEmail(branchDTO.getEmail());
        existing.setPhone(branchDTO.getPhone());
        existing.setAddress(branchDTO.getAddress());
        existing.setOpenTime(branchDTO.getOpenTime());
        existing.setCloseTime(branchDTO.getCloseTime());
        existing.setUpdatedAt(LocalDateTime.now());

        Branch updatedBranch = branchRepository.save(existing);
        return BranchMapper.toDTO(updatedBranch);
    }

    @Override
    public void deleteBranch(Long id) throws Exception {
        Branch existing =  branchRepository.findById(id).orElseThrow(
                () -> new Exception("branch not exist...")
        );
        branchRepository.delete(existing);
    }

    @Override
    public List<BranchDTO> getAllBranchesByStoreId(Long storeId) {
        List<Branch> branches =  branchRepository.findByStoreId(storeId);
        return branches.stream().map(BranchMapper::toDTO).collect(Collectors.toList());

    }

    @Override
    public BranchDTO getBranchById(Long id) throws Exception {
        Branch existing =  branchRepository.findById(id).orElseThrow(
                () -> new Exception("branch not exist...")
        );
        return BranchMapper.toDTO(existing);
    }
}
