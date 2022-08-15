import {Pagination} from "@mui/material";
import './Page.css'
import axios from "axios";
import City from "./City";
import Search from './Search'
import {useEffect, useState} from "react";
import React from 'react'

function Page() {
    const baseUrl = "http://localhost:8080"
    const pageSize = 10;
    const [maxPage, setMaxPage] = useState(1)
    const [currentPage, setCurrentPageNumber] = useState(1)
    const [searchedName, setSearchedName] = useState('')
    const [cities, setCities] = useState([])

    useEffect(() => getCities(searchedName, currentPage), [searchedName, currentPage])

    const getCities = (name, page) => {
        axios.get(`${baseUrl}/cities?page=${page}&name=${searchedName}`)
            .then(response => {
                const cities = response.data.cities
                const total = response.data.total_city_number
                setCities(cities)
                setMaxPage(Math.ceil(total / pageSize))
            })
            .catch(err => {
                console.log(`Error while loading cities, err: ${err}`)
            })
    }

    const onCityUpdateHandler = (updatedCity) => {
        let updatedCities = cities.map(city => {
            if (city.id === updatedCity.id) {
                return updatedCity
            }
            return city
        })

        setCities(updatedCities)
    }

    const onPageChangeHandler = (event, page) => {
        setCurrentPageNumber(page)
    }

    const searchByName = name => {
        setCurrentPageNumber(1)
        setSearchedName(name)
    }

    return (<div className="item-page">
        <Search searchByName={searchByName}/>
        <Pagination className="pagination-top" page={currentPage} count={maxPage} onChange={onPageChangeHandler} m="auto"/>
        <div className="city-list">
            {cities.map( city =>
                <City key={city.id} cityId={city.id} name={city.name} photo={city.photo} baseUrl={baseUrl} onCityUpdate={onCityUpdateHandler}/>
            )}
        </div>
        <Pagination className="pagination-bottom" page={currentPage} count={maxPage}  onChange={onPageChangeHandler} m="auto" />
    </div>)
}

export default Page;