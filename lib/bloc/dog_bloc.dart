import 'package:flutter_bloc/flutter_bloc.dart';
import '../repositories/dog_repository.dart';
import 'dog_event.dart';
import 'dog_state.dart';

class DogBloc extends Bloc<DogEvent, DogState> {
  final DogRepository repository;

  DogBloc({required this.repository}) : super(DogInitial()) {
    on<FetchDogImage>(_onFetchDogImage);
  }

  Future<void> _onFetchDogImage(
    FetchDogImage event,
    Emitter<DogState> emit,
  ) async {
    emit(DogLoading());
    try {
      final dogImage = await repository.getRandomDogImage();
      emit(DogLoaded(dogImage));
    } catch (e) {
      emit(DogError(e.toString()));
    }
  }
} 