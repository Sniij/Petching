package com.Petching.petching.carepost.mapper;

import com.Petching.petching.carepost.dto.CarePostDto;
import com.Petching.petching.carepost.entity.CarePost;
import com.Petching.petching.tag.conditionTag.CarePost_ConditionTag;
import com.Petching.petching.tag.locationTag.CarePost_LocationTag;
import org.mapstruct.Mapper;

import java.util.*;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface CarePostMapper {
//    CarePost carePostPostDtoToCarePost(CarePostDto.Post requestBody);
    default CarePost carePostPostDtoToCarePost(CarePostDto.Post requestBody) {
        if ( requestBody == null ) {
            return null;
        }

        CarePost.CarePostBuilder carePost = CarePost.builder();

        carePost.title( requestBody.getTitle() );
        carePost.content( requestBody.getContent() );
        List<String> list = requestBody.getImgUrls();
        if ( list != null ) {
            carePost.imgUrls( new ArrayList<String>( list ) );
        }
        carePost.startDay( requestBody.getStartDate().get("day") );
        carePost.startMonth( requestBody.getStartDate().get("month") );
        carePost.startYear( requestBody.getStartDate().get("year") );
        carePost.endDay( requestBody.getEndDate().get("day") );
        carePost.endMonth( requestBody.getEndDate().get("month") );
        carePost.endYear( requestBody.getEndDate().get("year") );

        return carePost.build();
    }

    CarePost carePostPatchDtoToCarePost(CarePostDto.Patch requestBody);

    default  CarePostDto.Response carePostToCarePostResponseDto(CarePost carePost) {
        if ( carePost == null ) {
            return null;
        }

        List<CarePost_ConditionTag> postConditionTags = carePost.getPostConditionTags();
        List<CarePost_LocationTag> postLocationTags = carePost.getPostLocationTags();

        String title = null;
        String content = null;
        List<String> imgUrls = new ArrayList<>();
        Integer startDay = null;
        Integer startMonth = null;
        Integer startYear = null;
        Integer endDay = null;
        Integer endMonth = null;
        Integer endYear = null;


        title = carePost.getTitle();
        content = carePost.getContent();
        imgUrls = carePost.getImgUrls();
        startDay = carePost.getStartDay();
        startMonth = carePost.getStartMonth();
        startYear = carePost.getStartYear();
        endDay = carePost.getEndDay();
        endMonth = carePost.getEndMonth();
        endYear = carePost.getEndYear();


        List<String> conditionTags = postConditionTagDtoResponse(postConditionTags);
        List<String> locationTags = postLocationTagDtoResponse(postLocationTags);

        // enddate 및 startdate 정의 및 초기화
        Map<String,Integer> startDate = new HashMap<>();
        Map<String,Integer> endDate = new HashMap<>();

        // enddate 및 statrdate 값 넣기
        startDate.put("day",startDay);
        startDate.put("month",startMonth);
        startDate.put("year",startYear);
        endDate.put("day",endDay);
        endDate.put("month",endMonth);
        endDate.put("year",endYear);


        CarePostDto.Response response =
                CarePostDto.Response.builder()
                        .title(title)
                        .content(content)
                        .imgUrls(imgUrls)
                        .startDate(startDate)
                        .endDate(endDate)
                        .conditionTags(conditionTags)
                        .locationTags(locationTags)
                        .build();

        return response;
    }

    //Todo : null발생 (수동매핑 필요)
    List<CarePostDto.Response> carePostsToCarePostResponseDtos(List<CarePost> carePosts);

    default List<String> postConditionTagDtoResponse (List<CarePost_ConditionTag> postConditionTags) {
        List<String> tagName = postConditionTags.stream().map(tag -> tag.getConditionTag().getBody())
                .collect(Collectors.toList());
        return tagName;
    }

    default List<String> postLocationTagDtoResponse (List<CarePost_LocationTag> postLocationTags) {
        List<String> tagName = postLocationTags.stream().map(tag -> tag.getLocationTag().getBody())
                .collect(Collectors.toList());
        return tagName;
    }

}
