package org.romanzhula.clear_sol_practical.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.romanzhula.clear_sol_practical.dto.StaffDTO;
import org.romanzhula.clear_sol_practical.models.Staff;

@Mapper
public interface StaffMapper {

    StaffMapper INSTANCE = Mappers.getMapper(StaffMapper.class);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "username", target = "username")
    @Mapping(source = "phoneNumber", target = "phoneNumber")
    @Mapping(source = "password", target = "password")
    Staff toStaffModel(StaffDTO staffDTO);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "username", target = "username")
    @Mapping(source = "phoneNumber", target = "phoneNumber")
    @Mapping(source = "password", target = "password")
    StaffDTO toStaffDTO(Staff staff);

}
