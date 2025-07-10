package org.mystock.service;

import java.util.List;

import org.mystock.exception.ResourceAlreadyExistsException;
import org.mystock.exception.ResourceNotFoundException;
import org.mystock.exception.UnableToProcessException;
import org.mystock.vo.UserVo;

public interface UserService {

	public UserVo findByUserName(String userName) throws ResourceNotFoundException;

	public UserVo findByEmail(String email) throws ResourceNotFoundException;
	
	public List<UserVo> findByUserNameOrEmailOrMobile(String userName, String email, String mobile) throws ResourceNotFoundException;

	public UserVo save(UserVo UserVo) throws UnableToProcessException, ResourceAlreadyExistsException;

	public UserVo update(UserVo UserVo) throws UnableToProcessException, ResourceNotFoundException;

	public boolean existsByUserName(String userName) throws ResourceNotFoundException;

	public boolean existsByEmail(String Email) throws ResourceNotFoundException;

	public List<UserVo> findAll() throws UnableToProcessException;
}