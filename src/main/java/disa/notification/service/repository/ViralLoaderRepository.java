package disa.notification.service.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import disa.notification.service.entity.ViralLoaderEntity;
import disa.notification.service.service.interfaces.PendingHealthFacilitySummary;
import disa.notification.service.service.interfaces.ViralLoaderResultSummary;
import disa.notification.service.service.interfaces.ViralLoaderResults;


public interface ViralLoaderRepository extends CrudRepository<ViralLoaderEntity,Integer> {

    @Query(value = "SELECT RequestingDistrictName as requestingDistrictName,RequestingFacilityCode healthFacilityLabCode,RequestingFacilityName as facilityName, AnalysisDateTime as processingDate,COUNT(*) as totalReceived, " +

                "COALESCE(SUM( CASE WHEN VIRAL_LOAD_STATUS ='PROCESSED' THEN 1 END),0) AS processed," +
                "COALESCE(SUM( CASE WHEN VIRAL_LOAD_STATUS ='PENDING' THEN 1 END),0)  as totalPending, "+
                "COALESCE(SUM( CASE WHEN VIRAL_LOAD_STATUS = 'NOT_PROCESSED' AND NOT_PROCESSING_CAUSE='NID_NOT_FOUND' THEN 1 END),0) AS notProcessedNidNotFount, " +
                "COALESCE(SUM( CASE WHEN VIRAL_LOAD_STATUS = 'NOT_PROCESSED' AND NOT_PROCESSING_CAUSE='INVALID_RESULT' THEN 1 END),0) AS notProcessedInvalidResult, "+
                "COALESCE(SUM( CASE WHEN VIRAL_LOAD_STATUS = 'NOT_PROCESSED' AND NOT_PROCESSING_CAUSE='DUPLICATE_NID' THEN 1 END),0) AS notProcessedDuplicateNid, "+
                "COALESCE(SUM( CASE WHEN VIRAL_LOAD_STATUS = 'NOT_PROCESSED' AND NOT_PROCESSING_CAUSE='DUPLICATED_REQUEST_ID' THEN 1 END),0) AS notProcessedDuplicatedRequestId "+
                " from VlData where RequestingFacilityCode in (:ouCodes) AND  CREATED_AT BETWEEN :startDateTime AND :endDateTime AND ENTITY_STATUS='ACTIVE' AND TypeOfResult='HIVVL' group by RequestingDistrictName,RequestingFacilityCode",nativeQuery = true)
    List<ViralLoaderResultSummary> findViralLoadResultSummary(@Param("startDateTime") LocalDateTime startDateTime, @Param("endDateTime") LocalDateTime endDateTime,@Param("ouCodes") Set<String> orgUnitCodes);

    @Query(value = "SELECT RequestId as requestId, UNIQUEID as nid, FIRSTNAME as firstName, SURNAME as lastName,RequestingDistrictName as requestingDistrictName,RequestingFacilityName as requestingFacilityName," +
            "RequestingFacilityCode as healthFacilityLabCode,CREATED_AT createdAt,UPDATED_AT updatedAt, VIRAL_LOAD_STATUS as viralLoadStatus, NOT_PROCESSING_CAUSE as notProcessingCause " +
            " from VlData where  RequestingFacilityCode in (:ouCodes) AND  CREATED_AT BETWEEN :startDateTime AND :endDateTime AND ENTITY_STATUS='ACTIVE' AND TypeOfResult='HIVVL'",nativeQuery = true)
    List<ViralLoaderResults> findViralLoadResults(@Param("startDateTime") LocalDateTime startDateTime, @Param("endDateTime") LocalDateTime endDateTime,@Param("ouCodes") Set<String> orgUnitCodes);

    @Query(value = "SELECT RequestId as requestId, UNIQUEID as nid, FIRSTNAME as firstName, SURNAME as lastName,RequestingDistrictName as requestingDistrictName,RequestingFacilityName as requestingFacilityName," +
            "RequestingFacilityCode as healthFacilityLabCode, VIRAL_LOAD_STATUS as viralLoadStatus, NOT_PROCESSING_CAUSE as notProcessingCause, " +
            "CREATED_AT as createdAt " +
            " from VlData where  RequestingFacilityCode in (:ouCodes) AND VIRAL_LOAD_STATUS='PENDING' AND  DATEDIFF(CURRENT_TIMESTAMP, CREATED_AT)>2 AND ENTITY_STATUS='ACTIVE' AND TypeOfResult='HIVVL'",nativeQuery = true)
    List<ViralLoaderResults> findViralLoadResultsPendingMoreThan2Days(@Param("ouCodes") Set<String> orgUnitCodes);

    @Query(value = "select VLPendente.requestingDistrictName,VLPendente.healthFacilityLabCode,VLPendente.facilityName,VLPendente.totalPending " +
            ",lastSync.lastSyncDate from (SELECT RequestingDistrictName as requestingDistrictName,RequestingFacilityCode as healthFacilityLabCode,RequestingFacilityName as facilityName,Count(RequestingDistrictName) as  totalPending " +
            "   from VlData where RequestingFacilityCode in (:ouCodes) AND VIRAL_LOAD_STATUS='PENDING' AND  DATEDIFF(CURRENT_TIMESTAMP, CREATED_AT)>2 AND ENTITY_STATUS = 'ACTIVE' AND TypeOfResult='HIVVL' group by RequestingDistrictName,RequestingFacilityCode ) VLPendente " +
            "  left join (SELECT RequestingDistrictName,RequestingFacilityCode as healthFacilityLabCode,max(UPDATED_AT) as lastSyncDate from VlData where RequestingFacilityCode in (:ouCodes) AND ENTITY_STATUS = 'ACTIVE' AND TypeOfResult='HIVVL'" +
            "  group by RequestingDistrictName,RequestingFacilityCode ) lastSync on VLPendente.healthFacilityLabCode=lastSync.healthFacilityLabCode and VLPendente.requestingDistrictName=lastSync.RequestingDistrictName",nativeQuery = true)
    List<PendingHealthFacilitySummary> findUnsincronizedHealthFacilities(@Param("ouCodes") Set<String> orgUnitCodes);
}
