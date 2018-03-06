package com.sidharth.demo.springcloud.core.ServiceImpl;

import com.sidharth.demo.springcloud.config.OAuth2Config;
import com.sidharth.demo.springcloud.core.Dto.StockDTO;
import com.sidharth.demo.springcloud.core.Dto.UserDTO;
import com.sidharth.demo.springcloud.core.Model.Stocks;
import com.sidharth.demo.springcloud.core.Model.User;
import com.sidharth.demo.springcloud.core.Repo.UserRepo;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author sidharthdash ON 2/28/18
 */

@Service("userDetailsService")
public class UserServiceImpl implements UserDetailsService{
    @Autowired
    UserRepo userRepo;

    @Autowired
    OAuth2Config oAuth2Config;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        return userRepo.findOneByUsername(userName);
    }

    public UserDetails addUser(String username,String secret){
        userRepo.addUser(username,secret,true);
        return userRepo.findOneByUsername(username);
    }

    public UserDTO userEntityToDTO(User user){

        if (null!=user) {
            ModelMapper modelMapper = new ModelMapper();
            modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
            UserDTO userDTO = modelMapper.map(user, UserDTO.class);
            return userDTO;
        }
        return null;
    }

    public User userDTOtoEntity(UserDTO userDTO){
        if(null!=userDTO) {
            ModelMapper modelMapper = new ModelMapper();
            modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
            User user = modelMapper.map(userDTO, User.class);
            return user;
        }
        return null;
    }
}
