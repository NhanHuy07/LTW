package com.example.demo.service.impl;

import com.example.demo.constants.ErrorMessage;
import com.example.demo.constants.SuccessMessage;
import com.example.demo.dto.request.AccessoryRequest;
import com.example.demo.dto.request.ProductRequest;
import com.example.demo.dto.response.MessageResponse;
import com.example.demo.entity.Accessory;
import com.example.demo.entity.Car;
import com.example.demo.entity.User;
import com.example.demo.repository.AccessoryRepository;
import com.example.demo.repository.AppointmentRepository;
import com.example.demo.repository.CarRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    @Value("${upload.path}")
    private String uploadPath;

    private final CarRepository carRepository;
    private final ModelMapper modelMapper;
    private final AccessoryRepository accessoryRepository;
    private final UserRepository userRepository;
    private final AppointmentRepository appointmentRepository;
    @Override
    public Car getCarById(Long carId) {
        return carRepository.findById(carId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ErrorMessage.CAR_NOT_FOUND));
    }

    @Override
    public Accessory getAccessoryById(Long accessoryId) {
        return accessoryRepository.findById(accessoryId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy sản phẩm"))
                ;
    }

    @Override
    @SneakyThrows
    @Transactional
    public MessageResponse editProduct(ProductRequest productRequest, MultipartFile file) {
        return saveProduct(productRequest, file, SuccessMessage.CAR_EDITED);
    }

    @Override
    @SneakyThrows
    @Transactional
    public MessageResponse addProduct(ProductRequest productRequest, MultipartFile file) {
        return saveProduct(productRequest,file, SuccessMessage.CAR_ADDED);
    }

    @Override
    @SneakyThrows
    @Transactional
    public MessageResponse editAccessory(AccessoryRequest accessoryRequest, MultipartFile file) {
        return saveAccessory(accessoryRequest, file, "Phụ tùng đã được sửa thành công");
    }

    @Override
    @SneakyThrows
    @Transactional
    public MessageResponse addAccessory(AccessoryRequest accessoryRequest, MultipartFile file) {
        return saveAccessory(accessoryRequest,file,"Phụ tùng đã được thêm thành công");
    }

    @Override
    public MessageResponse lockUser(Long id) {
        Optional<User> userOpt = userRepository.findById(id);
        userOpt.ifPresent(user -> {
            user.setActive(false);
            userRepository.save(user);
        });
        return new MessageResponse("alert-success", "Đã khóa thành công");
    }

    @Override
    public MessageResponse unlockUser(Long id) {
        Optional<User> userOpt = userRepository.findById(id);
        userOpt.ifPresent(user -> {
            user.setActive(true);
            userRepository.save(user);
        });
        return new MessageResponse("alert-success", "Mở khóa thành công");
    }

    @Override
    public MessageResponse deleteCar(Long id) {
        if(carRepository.existsById(id)){
            carRepository.deleteById(id);
            return new MessageResponse("alert-success", "Xóa thành công");
        }
        return new MessageResponse("alert-danger", "Không tìm thấy xe!");
    }

    @Override
    public MessageResponse deleteAccessory(Long id) {
        if(accessoryRepository.existsById(id)){
            accessoryRepository.deleteById(id);
            return new MessageResponse("alert-success", "Xóa thành công");
        }
        return new MessageResponse("alert-danger", "Không tìm thấy phụ tùng!");
    }

    @Override
    public MessageResponse deleteAppointment(Long id) {
        if (appointmentRepository.existsById(id)){
            appointmentRepository.deleteById(id);
            return new MessageResponse("alert-success", "Đã hủy thành công");
        }
        return new MessageResponse("alert-danger", "Không tìm thấy cuộc hẹn!");
    }

    private MessageResponse saveProduct(ProductRequest productRequest, MultipartFile file, String message) throws IOException {
        Car car = modelMapper.map(productRequest, Car.class);
        if (file != null && !file.getOriginalFilename().isEmpty()) {
            File uploadDir = new File(uploadPath);

            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "-" + file.getOriginalFilename();
            file.transferTo(new File(uploadPath + "/" + resultFilename));
            car.setFileName(resultFilename);
        }
        carRepository.save(car);
        return new MessageResponse("alert-success", message);
    }

    private MessageResponse saveAccessory(AccessoryRequest accessoryRequest, MultipartFile file, String message) throws IOException {
        Accessory accessory = modelMapper.map(accessoryRequest, Accessory.class);
        if (file != null && !file.getOriginalFilename().isEmpty()) {
            File uploadDir = new File(uploadPath);

            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "-" + file.getOriginalFilename();
            file.transferTo(new File(uploadPath + "/" + resultFilename));
            accessory.setImage(resultFilename);
        }
        accessoryRepository.save(accessory);
        return new MessageResponse("alert-success", message);
    }
}
