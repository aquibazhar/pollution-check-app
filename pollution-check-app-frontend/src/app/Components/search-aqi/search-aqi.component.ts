import { Component, OnInit } from '@angular/core'
import { MatSnackBar } from '@angular/material/snack-bar'
import { CityData } from 'src/app/Models/city-data'
import { IqairService } from 'src/app/Services/iqair.service'
import { WatchlistService } from 'src/app/Services/watchlist.service'

@Component({
  selector: 'app-search-aqi',
  templateUrl: './search-aqi.component.html',
  styleUrls: ['./search-aqi.component.css'],
})
export class SearchAqiComponent implements OnInit {
  tempCountries: any
  tempStates: any
  tempCities: any
  tempNearestCity: any
  pollution: any
  aqius!: number
  aqiusNearest!: number
  selectedCountry: string = ''
  selectedState: string = ''
  selectedCity: string = ''
  selectedStation = ''
  cardSubtitle = 'NEAREST CITY LIVE AQI INDEX'
  healthStatus = ''
  location = ''

  cityData: CityData = new CityData('', '', '', '', 0, '')

  constructor(
    private iqair: IqairService,
    private watchlist: WatchlistService,
    private _snackBar: MatSnackBar,
  ) {}

  ngOnInit(): void {
    this.iqair.getCountries().subscribe((country) => {
      this.tempCountries = country.data
    },
    (error) => {
      console.log(error)
      this.openSnackBar(String(error).substring(7), 'Ok')
    })
    this.iqair.getNearestAqi().subscribe((data) => {
      this.tempNearestCity = data
      this.aqiusNearest = this.tempNearestCity.data.current.pollution.aqius
      this.changeHealthStatus(this.aqiusNearest)
      this.location =
        this.tempNearestCity.data.city +
        ', ' +
        this.tempNearestCity.data.state +
        ', ' +
        this.tempNearestCity.data.country
    },
    (error) => {
      console.log(error)
      this.openSnackBar(String(error).substring(7), 'Ok')
    })
  }
  onSelectCountry(country: any) {
    this.iqair.getStates(String(country.value)).subscribe((state) => {
      this.tempStates = state.data
    },
    (error) => {
      console.log(error)
      this.openSnackBar(String(error).substring(7), 'Ok')
    })
    console.log(this.tempStates)
  }
  onSelectState(state: any) {
    this.iqair
      .getCities(this.selectedCountry, String(state.value))
      .subscribe((city) => {
        this.tempCities = city.data
      },
      (error) => {
        console.log(error)
        this.openSnackBar(String(error).substring(7), 'Ok')
      })
  }

  onSubmit() {
    this.iqair
      .getDataStation(
        this.selectedCountry,
        this.selectedState,
        this.selectedCity,
      )
      .subscribe((data) => {
        this.tempNearestCity = data
        this.aqiusNearest = this.tempNearestCity.data.current.pollution.aqius
        this.changeHealthStatus(this.aqiusNearest)
      },
      (error) => {
        console.log(error)
        this.openSnackBar(String(error).substring(7), 'Ok')
      })
    this.cardSubtitle = 'LIVE AQI INDEX'

    this.location =
      this.selectedCity +
      ', ' +
      this.selectedState +
      ', ' +
      this.selectedCountry
  }

  changeHealthStatus(aqi: number) {
    if (aqi <= 50) {
      this.healthStatus = 'Healthy'
    } else if (aqi > 50 && aqi <= 100) {
      this.healthStatus = 'Moderate'
    } else if (aqi > 100 && aqi <= 150) {
      this.healthStatus = 'Sensitive'
    } else {
      this.healthStatus = 'Unhealthy'
    }
  }

  openSnackBar(message: string, action: string) {
    this._snackBar.open(message, action, {
      duration: 2000,
      panelClass: ['blue-snackbar'],
    })
  }
  // WATCHLIST

  addToWatchList(healthStatus: string, location: string, aqiUS: number) {
    let locationData: string[] = location.split(',')
    let email = localStorage.getItem('email')
    if (email == null) {
      email = 'nouser'
    }
    let city = locationData[0].trim()
    let state = locationData[1].trim()
    let country = locationData[2].trim()
    this.cityData = new CityData(
      email,
      city,
      state,
      country,
      aqiUS,
      healthStatus,
    )
    console.log(this.cityData)
    this.watchlist.addToWatchList(this.cityData).subscribe(
      (data) => {
        console.log(data)
        this.openSnackBar('Added successfully to your watchlist', 'Ok')
      },
      (error) => {
        console.log(error)
        this.openSnackBar(String(error).substring(7), 'Ok')
      },
    )
  }
}
