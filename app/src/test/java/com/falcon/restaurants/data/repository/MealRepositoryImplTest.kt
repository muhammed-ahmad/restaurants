package com.falcon.restaurants.data.repository

import com.falcon.restaurants.data.DataTestData
import com.falcon.restaurants.data.db.dao.MealDataDao
import com.falcon.restaurants.data.db.model.MealData
import com.falcon.restaurants.data.mapper.MealDataMapper
import com.falcon.restaurants.data.net.RetrofitInterface
import com.falcon.restaurants.domain.interactor.DomainTestData
import com.falcon.restaurants.domain.model.Meal
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Spy
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify

@RunWith(MockitoJUnitRunner::class)
class MealRepositoryImplTest {

    lateinit var SUT: MealRepositoryImpl
    @Mock lateinit var mealDataDao: MealDataDao
    @Mock lateinit var  retrofitInterface: RetrofitInterface
    @Spy lateinit var  mealDataMapper: MealDataMapper

    val meals = DomainTestData.createMeals()
    val mealDatasOfTwo = mutableListOf(DataTestData.createMealData1(), DataTestData.createMealData3())
    val mealData2 = DataTestData.createMealData2()
    val mealDtos = DataTestData.createMealDtos()

    val RESTAURANT2 = DomainTestData.createRestaurant2()

    @Before
    fun setUp(){
        SUT = MealRepositoryImpl(mealDataDao, retrofitInterface, mealDataMapper)
    }

    @Test
    fun getMealsByRestaurantId_WhenRestaurantIdFound_ReturnThisRestaurantMeals(){
        // arrange
        Mockito.`when`(mealDataDao.getMealsByRestaurantId(RESTAURANT2.id)).thenReturn(Observable.just(mealDatasOfTwo))
        // act
        val testObserver: TestObserver<List<Meal>> = SUT.getMealsByRestaurantId(RESTAURANT2.id).test()
        // assert
        testObserver.assertComplete()
        Mockito.verify(mealDataDao).getMealsByRestaurantId(RESTAURANT2.id)
        testObserver.assertValue { meals -> meals[0].restaurantId.equals(RESTAURANT2.id) }
        testObserver.assertValue { meals -> meals.size == 2 }
        testObserver.assertValue { meals -> meals is List<Meal> }
        testObserver.assertValueCount(1)
    }

    @Test
    fun getMealsByRestaurantId_WhenRestaurantIdNotFound_ReturnEmptyList(){
        // arrange
        val notFoundId = "id1"
        val mealDatasEmpty = mutableListOf<MealData>()
        Mockito.`when`(mealDataDao.getMealsByRestaurantId(notFoundId)).thenReturn(Observable.just(mealDatasEmpty))
        // act
        val testObserver: TestObserver<List<Meal>> = SUT.getMealsByRestaurantId(notFoundId).test()
        // assert
        testObserver.assertComplete()
        testObserver.assertValue { meals -> meals is List<Meal> }
        testObserver.assertValue { meals -> meals.size == 0 }
    }

    @Test
    fun getMealById_WhenMealIdFound_ReturnMealById(){
        // arrange
        Mockito.`when`(mealDataDao.getMealById(mealData2.id)).thenReturn(Single.just(mealData2))
        // act
        val testObserver: TestObserver<Meal> = SUT.getMealById(mealData2.id).test()
        // assert
        testObserver.assertComplete()
        Mockito.verify(mealDataDao).getMealById(mealData2.id)
        testObserver.assertValue { meal -> meal is Meal }
        testObserver.assertValue { meal -> meal.id.equals("id2") }
        testObserver.assertValueCount(1)
    }

    @Test
    fun getMealById_WhenMealIdNotFound_ReturnError(){
        // arrange
        val notFoundId = "id1"
        val throwable = Throwable("Item Not Found")
        Mockito.`when`(mealDataDao.getMealById(notFoundId)).thenReturn(Single.error(throwable))
        // act
        val testObserver: TestObserver<Meal> = SUT.getMealById(notFoundId).test()
        // assert
        testObserver.assertNotComplete()
        testObserver.assertError(throwable)
    }

    @Test
    fun upsert_WhenUpsertListOfMeals_ReturnCompletable(){
        // arrange
        // act
        val testObserver = SUT.upsert(meals).test()
        // assert
        testObserver.assertComplete()
    }

    @Test
    fun fetchMeals_WhenNotUpdated_ReturnNonEmptyList(){
        // arrange
        val maxUpdatedAt = "1970-01-01 00:00:01"
        Mockito.`when`(mealDataDao.getMaxUpdated()).thenReturn(maxUpdatedAt)
        Mockito.`when`(retrofitInterface.fetchMealDtos(maxUpdatedAt)).thenReturn(Single.just(mealDtos))
        // act
        val testObserver: TestObserver<List<Meal>> = SUT.fetchMeals().test()
        // assert
        testObserver.assertComplete()
        testObserver.assertValue { meals -> meals[0].id.equals("id1") }
        testObserver.assertValue { meals -> meals.size == 3 }
        testObserver.assertValue { meals -> meals is List<Meal> }
        testObserver.assertValueCount(1)
    }

    @Test
    fun fetchMeals_WhenUpdated_ReturnEmptyList(){
        // arrange
        val maxUpdatedAt = "2022-08-01 00:00:01"
        Mockito.`when`(mealDataDao.getMaxUpdated()).thenReturn(maxUpdatedAt)
        Mockito.`when`(retrofitInterface.fetchMealDtos(maxUpdatedAt)).thenReturn(Single.just(mutableListOf()))
        // act
        val testObserver: TestObserver<List<Meal>> = SUT.fetchMeals().test()
        // assert
        testObserver.assertComplete()
        testObserver.assertValue { meals -> meals.size == 0 }
        testObserver.assertValue { meals -> meals is List<Meal> }
    }
}