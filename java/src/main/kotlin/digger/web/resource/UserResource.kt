/*
 * Digger
 * Copyright (C) 2019-2022 Hildeberto Mendonca
 *
 * This program is free software: you can redistribute it and/or modify it under the terms
 * of the GNU General Public License as published by the Free Software Foundation,
 * either version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 *
 * A full copy of the GNU General Public License is available at:
 * https://github.com/htmfilho/digger/blob/master/LICENSE
 */
package digger.web.resource

import digger.model.UserDto
import digger.service.RoleService
import digger.service.UserService
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
class UserResource(private val userService: UserService, private val roleService: RoleService) {
    @get:GetMapping(
        value = ["/api/admin/users"],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    val users: List<UserDto>
        get() {
            val users = userService.findAll()
            val usersDTO: MutableList<UserDto> = ArrayList()
            for (user in users) {
                val role = roleService.findByUsername(user.username)
                val userDTO = UserDto(
                    user.id, user.firstName, user.lastName, user.username,
                    user.enabled, role.authority
                )
                usersDTO.add(userDTO)
            }
            return usersDTO
        }

    @PostMapping(value = ["/api/admin/users"])
    fun saveUser(@RequestParam(value = "username", defaultValue = "") username: String?) {
        val user = userService.findByUsername(username)
        userService.enableOrDisableUser(user)
    }

    @DeleteMapping("/api/admin/users/{userId}")
    fun deleteUser(@PathVariable userId: Long?) {
        userService.delete(userId)
    }
}