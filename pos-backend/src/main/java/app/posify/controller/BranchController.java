package app.posify.controller;

import app.posify.exceptions.UserException;
import app.posify.modal.User;
import app.posify.payload.dto.BranchDTO;
import app.posify.payload.response.ApiResponse;
import app.posify.service.BranchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/branches")
public class BranchController {

    private final BranchService branchService;

    @PostMapping
    public ResponseEntity<BranchDTO> createBranch(@RequestBody BranchDTO branchDTO) throws UserException {
        BranchDTO createdBranch = branchService.createBranch(branchDTO);
        return ResponseEntity.ok(createdBranch);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BranchDTO> getBranchById(
            @PathVariable Long id
    ) throws Exception {
        BranchDTO createdBranch = branchService.getBranchById(id);
        return ResponseEntity.ok(createdBranch);
    }

    @GetMapping("/store/{storeId}")
    public ResponseEntity<List<BranchDTO>> getAllBranchesByStoreId(
            @PathVariable Long storeId
    ) throws Exception {
        List<BranchDTO> createdBranch = branchService.getAllBranchesByStoreId(storeId);
        return ResponseEntity.ok(createdBranch);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BranchDTO> updateBranch(
            @PathVariable Long id,
            @RequestBody BranchDTO branchDTO
    ) throws Exception {
        BranchDTO createdBranch = branchService.updateBranch(id, branchDTO);
        return ResponseEntity.ok(createdBranch);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteBranch(
            @PathVariable Long id
    ) throws Exception {
        branchService.deleteBranch(id);
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage("branch deleted successfully");
        return ResponseEntity.ok(apiResponse);
    }


}
