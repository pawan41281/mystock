package org.mystock.service;

import org.mystock.exception.ResourceAlreadyExistsException;
import org.mystock.exception.ResourceNotFoundException;
import org.mystock.exception.UnableToProcessException;
import org.mystock.vo.UserVo;

import java.util.List;

public interface UserService {

	UserVo findById(Long id) throws ResourceNotFoundException;

	UserVo findByIdAndPassword(Long id, String password) throws ResourceNotFoundException;

	UserVo findByUserId(String userName) throws ResourceNotFoundException;

	UserVo findByEmail(String email) throws ResourceNotFoundException;
	
	List<UserVo> find(String userName, String email, String mobile) throws ResourceNotFoundException;

	UserVo save(UserVo UserVo) throws UnableToProcessException, ResourceAlreadyExistsException;

	UserVo update(UserVo UserVo) throws UnableToProcessException, ResourceNotFoundException;

	boolean updateStatus(Long id, boolean status) throws UnableToProcessException, ResourceNotFoundException;

	boolean existsByUserId(String userName) throws ResourceNotFoundException;

	boolean existsByEmail(String Email) throws ResourceNotFoundException;

	List<UserVo> findAll() throws UnableToProcessException;
}