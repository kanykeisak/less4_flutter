import 'package:equatable/equatable.dart';

abstract class DogEvent extends Equatable {
  const DogEvent();

  @override
  List<Object?> get props => [];
}

class FetchDogImage extends DogEvent {} 