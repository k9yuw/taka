package taka.takaspring.Membership.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import taka.takaspring.Membership.dto.EnrollmentDto;
import taka.takaspring.Membership.dto.MembershipDto;
import taka.takaspring.Membership.service.MembershipService;

import java.util.List;

// 특정 단체의 관리자가 다룰 수 있는 기능들
@PreAuthorize("@accessControlService.isOrgAdmin(principal, #organization_id)")
@RestController
@RequestMapping("/api/admin/{organization_id}")
public class MembershipController {

    private final MembershipService membershipService;

    @Autowired
    public MembershipController(MembershipService userOrgService) {
        this.membershipService = userOrgService;
    }

    // 단체의 회원 목록을 조회
    @GetMapping("/membership/users")
    public ResponseEntity<List<MembershipDto.UserByOrgResponse>> getUserListByOrgId(@PathVariable("organization_id") Long orgId) {
        List<MembershipDto.UserByOrgResponse> userList = membershipService.getUserListByOrgId(orgId);
        return ResponseEntity.ok().body(userList);
    }

    // 단체에 회원 삭제
    @DeleteMapping("/users/{user_id}")
    public ResponseEntity<Void> deleteUserFromOrganization(
            @PathVariable("organization_id") Long orgId,
            @PathVariable("user_id") Long userId) {
        membershipService.deleteUserFromOrg(orgId, userId);
        return ResponseEntity.ok().build();
    }

    // 입부 신청한 회원 목록 조회
    @GetMapping("/membership/enrolled")
    public ResponseEntity<List<EnrollmentDto.EnrollmentIntermediateRequest>> getPendingUserList(@PathVariable("organization_id") Long orgId) {
        List<EnrollmentDto.EnrollmentIntermediateRequest> pendingUserList = membershipService.getPendingUserList(orgId);
        return ResponseEntity.ok().body(pendingUserList);
    }

    // 입부 승인
    @PatchMapping("/membership/approve")
    public ResponseEntity<EnrollmentDto.EnrollmentResponse> approveEnrollment(@RequestBody EnrollmentDto.EnrollmentIntermediateRequest request) {
        EnrollmentDto.EnrollmentResponse response = membershipService.approveEnrollment(request);
        return ResponseEntity.ok().body(response);
    }

    // 입부 거절
    @PatchMapping("/membership/reject")
    public ResponseEntity<EnrollmentDto.EnrollmentResponse> rejectEnrollment(@RequestBody EnrollmentDto.EnrollmentIntermediateRequest request) {
        EnrollmentDto.EnrollmentResponse response = membershipService.rejectEnrollment(request);
        return ResponseEntity.ok().body(response);
    }
}