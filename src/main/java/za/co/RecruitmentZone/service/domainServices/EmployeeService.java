package za.co.RecruitmentZone.service.domainServices;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import za.co.RecruitmentZone.entity.domain.Employee;
import za.co.RecruitmentZone.entity.domain.EmployeeDTO;
import za.co.RecruitmentZone.repository.EmployeeRepository;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    String domainDotZa = "kiunga.co.za";
    String domain = "kiunga";

    private final Logger log = LoggerFactory.getLogger(EmployeeService.class);
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee createEmployee(EmployeeDTO employeeDTO){
        Employee newEmp = new Employee();
        newEmp.setFirst_name(employeeDTO.getFirst_name().strip());
        newEmp.setLast_name(employeeDTO.getLast_name());
        newEmp.setContact_number(employeeDTO.getContact_number());
        String userName = createUserNameAndEmail(employeeDTO.getFirst_name(),employeeDTO.getLast_name());
        newEmp.setUsername(userName);
        newEmp.setEmail_address(userName);
        return employeeRepository.save(newEmp);

    }

    public void updateExistingEmployee(Long employeeID,EmployeeDTO employeeDTO){
        log.info("--updateExistingEmployee---");
        Optional<Employee> oe = employeeRepository.findById(employeeID);
        log.info("--findById -employeeID--");
        Employee newEmp = new Employee();
        log.info("--newEmp -newEmp--{}",newEmp);
        if (oe.isPresent()){
            log.info("--oe is PRESENT{} ",oe);
            newEmp = oe.get();
            newEmp.setFirst_name(employeeDTO.getFirst_name());
            newEmp.setLast_name(employeeDTO.getLast_name());
            newEmp.setContact_number(employeeDTO.getContact_number());
            String userName = createUserNameAndEmail(employeeDTO.getFirst_name(),employeeDTO.getLast_name());
            newEmp.setUsername(userName);
            newEmp.setEmail_address(userName);
        }
        employeeRepository.save(newEmp);

    }


    public String createUserNameAndEmail(String firstName,String LastName){
        return firstName+"."+LastName+"@"+domainDotZa;
    }
    public Optional<Employee> findByUsername(String username) {
        return  employeeRepository.findByUsername(username);
    }

    public List<Employee> getEmployees() {
        return employeeRepository.findAll();
    }

    public Optional<Employee> findEmployeeByID(Long employeeID) {
        return employeeRepository.findById(employeeID);
    }
    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }

    public EmployeeDTO convertToDTO(Long employeeID){
        Optional<Employee> oe = employeeRepository.findById(employeeID);
        EmployeeDTO dto = new EmployeeDTO("","","011000000");
        if(oe.isPresent()){
            dto = new EmployeeDTO(oe.get().getFirst_name(),oe.get().getLast_name(),oe
                    .get().getContact_number());
        }
        return dto;
    }

}
