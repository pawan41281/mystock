package org.mystock.service;

import org.mystock.exception.ResourceAlreadyExistsException;
import org.mystock.exception.ResourceNotFoundException;
import org.mystock.exception.UnableToProcessException;
import org.mystock.vo.UserVo;

import java.util.List;

public interface UserService {

	public UserVo findByUserId(String userName) throws ResourceNotFoundException;

	public UserVo findByEmail(String email) throws ResourceNotFoundException;
	
	public List<UserVo> find(String userName, String email, String mobile) throws ResourceNotFoundException;

	public UserVo save(UserVo UserVo) throws UnableToProcessException, ResourceAlreadyExistsException;

	public UserVo update(UserVo UserVo) throws UnableToProcessException, ResourceNotFoundException;

	public boolean existsByUserId(String userName) throws ResourceNotFoundException;

	public boolean existsByEmail(String Email) throws ResourceNotFoundException;

	public List<UserVo> findAll() throws UnableToProcessException;
}