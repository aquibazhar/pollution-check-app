import { Component, OnInit } from '@angular/core'
import { MatSnackBar } from '@angular/material/snack-bar'
import { CityData } from 'src/app/Models/city-data'
import { IqairService } from 'src/app/Services/iqair.service'
import { WatchlistService } from 'src/app/Services/watchlist.service'

@Component({
  selector: 'app-watch-list',
  templateUrl: './watch-list.component.html',
  styleUrls: ['./watch-list.component.css'],
})
export class WatchListComponent implements OnInit {
  data: CityData[] = []

  constructor(
    private watchlist: WatchlistService,
    private _snackBar: MatSnackBar,
    private iqair: IqairService,
  ) {}

  ngOnInit(): void {
    let email = localStorage.getItem('email')
    if (email == null) {
      email = 'nouser'
    }
    this.watchlist.getWatchListByEmail(email).subscribe((data) => {
      console.log(data)
      this.data = data
    })
  }

  deleteRecord(id: number | undefined) {
    console.log(id)
    this.watchlist.removeFromWatchList(id).subscribe((data) => {
      console.log(data)
      this.openSnackBar('Record deleted successfully', 'Ok')
      this.ngOnInit()
    })
  }
  updateRecord(cityData: CityData) {
    this.iqair
      .getDataStation(cityData.country, cityData.state, cityData.city)
      .subscribe((data) => {
        let newCityData = new CityData(
          cityData.userEmail,
          data.data.city,
          data.data.state,
          data.data.country,
          Number(data.data.current.pollution.aqius),
          this.calculateHealthStatus(data.data.current.pollution.aqius),
          cityData.id,
        )
        this.watchlist.updateDataInWatchList(newCityData).subscribe((data) => {
          console.log(data)
          this.openSnackBar('Record updated successfully', 'Ok')
          this.ngOnInit()
        })
      })
  }

  calculateHealthStatus(aqi: number) {
    if (aqi <= 50) {
      return 'Healthy'
    } else if (aqi > 50 && aqi <= 100) {
      return 'Moderate'
    } else if (aqi > 100 && aqi <= 150) {
      return 'Sensitive'
    } else {
      return 'Unhealthy'
    }
  }

  openSnackBar(message: string, action: string) {
    this._snackBar.open(message, action, {
      duration: 2000,
      panelClass: ['blue-snackbar'],
    })
  }
}
